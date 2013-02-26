package org.cloudlet.web.core.servlet;

import com.google.inject.Singleton;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.ProxyHost;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public class ProxyFilter implements Filter {

  private static final Logger logger = Logger.getLogger(ProxyFilter.class.getName());

  public static void transferStream(InputStream is, OutputStream... outs) throws IOException {
    if (is == null || outs == null) {
      throw new IOException("Invalid streams");
    }

    try {
      byte[] buffer = new byte[4096];
      int read = 0;

      while ((read = is.read(buffer)) > 0) {
        for (OutputStream os : outs) {
          os.write(buffer, 0, read);
        }
      }
    } finally {
      try {
        for (OutputStream os : outs) {
          os.close();
        }
      } catch (Throwable t) {
      }

      try {
        is.close();
      } catch (Throwable t) {
      }
    }
  }

  private static String getStreamAsString(InputStream stream, String charset) throws IOException {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
      StringWriter writer = new StringWriter();

      char[] chars = new char[256];
      int count = 0;
      while ((count = reader.read(chars)) > 0) {
        writer.write(chars, 0, count);
      }

      return writer.toString();
    } finally {
      if (stream != null) {
        stream.close();
      }
    }
  }

  public Set<String> staticExtensions;

  public Map<String, HttpClient> clients;

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    StringBuffer sb = req.getRequestURL();
    String qs = req.getQueryString();
    if (qs != null && qs.length() > 0) {
      sb.append("?").append(qs);
    }
    URL url = new URL(sb.toString());
    String method = req.getMethod();
    logger.info(method + " " + url);
    logger.info("Current Thread:" + Thread.currentThread());
    logger.info("This: " + this);

    String host = url.getHost();
    if ("127.0.0.1".equals(host) || "localhost".equals(host)) {
      chain.doFilter(request, response);
      return;
    }

    String uri = req.getRequestURI();

    File localFile = new File("D:/Code/cloudlet/web/app/mirrors/" + host + uri + (uri.endsWith("/") ? "index.html" : ""));
    if (localFile.exists() && isStatic(uri)) {
      transferStream(new FileInputStream(localFile), resp.getOutputStream());
    } else {
      File parent = localFile.getParentFile();
      if (!parent.exists()) {
        parent.mkdirs();
      }

      HttpClient client = getClient(host);

      HttpMethod httpMethod;
      if ("GET".equals(method)) {
        httpMethod = new GetMethod(sb.toString());
      } else if ("HEAD".equals(method)) {
        httpMethod = new HeadMethod(sb.toString());
      } else if ("PUT".equals(method)) {
        httpMethod = new PutMethod(sb.toString());
      } else if ("DELETE".equals(method)) {
        httpMethod = new DeleteMethod(sb.toString());
      } else {
        httpMethod = new PostMethod(sb.toString());
      }

      if (httpMethod instanceof EntityEnclosingMethod) {
        EntityEnclosingMethod m = (EntityEnclosingMethod) httpMethod;
        RequestEntity re = new InputStreamRequestEntity(req.getInputStream());
        m.setRequestEntity(re);
      }

      Enumeration<String> headers = req.getHeaderNames();
      while (headers.hasMoreElements()) {
        String key = headers.nextElement();
        if ("If-Modified-Since".equalsIgnoreCase(key)) {
          continue;
        }
        Enumeration<String> values = req.getHeaders(key);
        while (values.hasMoreElements()) {
          String value = values.nextElement();
          httpMethod.addRequestHeader(key, value);
        }
      }

      ProxyHost proxy = new ProxyHost("127.0.0.1", 8888);
      client.getHostConfiguration().setProxyHost(proxy);
      client.executeMethod(httpMethod);

      int code = httpMethod.getStatusCode();
      String status = code + " " + httpMethod.getStatusText();
      if (code >= 400) {
        logger.severe(status);
      } else {
        logger.info(status);
      }

      resp.setStatus(code);
      for (Header header : httpMethod.getResponseHeaders()) {
        resp.setHeader(header.getName(), header.getValue());
      }

      InputStream respStream = httpMethod.getResponseBodyAsStream();
      if (respStream != null) {
        Header enc = httpMethod.getResponseHeader("Content-Encoding");
        if (enc != null && "gzip".equalsIgnoreCase(enc.getValue())) {
          GZIPInputStream gzipInput = new GZIPInputStream(respStream);
          transferStream(gzipInput, new FileOutputStream(localFile), new GZIPOutputStream(resp.getOutputStream()));
        } else {
          transferStream(respStream, new FileOutputStream(localFile), resp.getOutputStream());
        }
      }
    }
  }

  @Override
  public void init(FilterConfig config) throws ServletException {
    staticExtensions = new HashSet<String>();
    staticExtensions.add(".js");
    staticExtensions.add(".css");
    staticExtensions.add(".html");
    staticExtensions.add(".png");
    staticExtensions.add(".jpg");

    clients = new HashMap<String, HttpClient>();
  }

  private synchronized HttpClient getClient(String host) {
    HttpClient client = clients.get(host);
    if (client == null) {
      client = new HttpClient();
      clients.put(host, client);
    }
    return client;
  }

  private boolean isStatic(String uri) {
    int index = uri.lastIndexOf(".");
    if (index >= 0) {
      String ext = uri.substring(index);
      if (staticExtensions.contains(ext)) {
        return true;
      }
    }
    return false;
  }
}
