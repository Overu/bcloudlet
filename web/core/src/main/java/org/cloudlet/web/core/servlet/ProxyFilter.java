package org.cloudlet.web.core.servlet;

import com.google.inject.Singleton;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
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
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
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

  public static synchronized HttpClient getClient(String host) {
    HttpClient client = clients.get(host);
    if (client == null) {
      client = new HttpClient();
      clients.put(host, client);
    }
    return client;
  }

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
        t.printStackTrace();
      }

      try {
        is.close();
      } catch (Throwable t) {
        t.printStackTrace();
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

  public static Map<String, HttpClient> clients;

  private String mirror = "book.duokan.com";

  private Set<String> localAddresses;

  private String dataLocation;

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;
    String uri1 = req.getRequestURI();
    StringBuffer sb = req.getRequestURL();
    String qs = req.getQueryString();
    if (qs != null && qs.length() > 0) {
      qs = qs.replace("deviceid=undefined", "deviceid=D002-F5805035-921D-4426-BF91-81F65004FEFC");
      sb.append("?").append(qs);
    }
    URL url = new URL(sb.toString());
    String method = req.getMethod();
    String host = url.getHost();
    InetAddress requestHost = InetAddress.getByName(host);
    if (localAddresses.contains(requestHost.getHostAddress())) {
      chain.doFilter(request, response);
      return;
    }

    File mirror = getMirror(host, req.getRequestURI(), qs);
    if (mirror.exists()) {
      String contentType = "text/html";
      String path = mirror.getPath();
      int index = path.lastIndexOf(".");
      if (index > 0) {
        String ext = path.substring(index);
        if (ext.equals(".json")) {
          contentType = "application/json";
        } else if (ext.equals(".xml")) {
          contentType = "application/xml";
        } else if (ext.equals(".png")) {
          contentType = "image/png";
        } else if (ext.equals(".jpg") || ext.equals(".jpg!s")) {
          contentType = "image/jpg";
        } else if (ext.equals(".html")) {
          contentType = "text/html";
        }
      }
      resp.setContentType(contentType);
      transferStream(new FileInputStream(mirror), resp.getOutputStream());
    } else {
      File parent = mirror.getParentFile();
      if (!parent.exists()) {
        parent.mkdirs();
      }
      HttpClient client = new HttpClient();

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
        if ("If-Modified-Since".equalsIgnoreCase(key) || "If-None-Match".equalsIgnoreCase(key)) {
          continue;
        }
        Enumeration<String> values = req.getHeaders(key);
        while (values.hasMoreElements()) {
          String value = values.nextElement();
          httpMethod.addRequestHeader(key, value);
          System.out.println(key + ":" + value);
        }
      }
      if (host.equals("book.duokan.com")) {
        httpMethod.addRequestHeader("Cookie",
            "build=2012120701; device=D002-F5805035-921D-4426-BF91-81F65004FEFC; token=; userid=fantongx@gmail.com");
      }

      // ProxyHost proxy = new ProxyHost("127.0.0.1", 8888);
      // client.getHostConfiguration().setProxyHost(proxy);

      client.executeMethod(httpMethod);

      int statusCode = httpMethod.getStatusCode();
      System.out.println(statusCode + " " + httpMethod.getStatusText() + " " + url);

      resp.setStatus(statusCode);
      for (Header header : httpMethod.getResponseHeaders()) {
        if (header.getName().equalsIgnoreCase("Transfer-Encoding")) {
          continue;
        }
        resp.setHeader(header.getName(), header.getValue());
      }

      if (statusCode == 200 || statusCode == 201) {
        InputStream respStream = httpMethod.getResponseBodyAsStream();
        if (respStream != null) {
          Header enc = httpMethod.getResponseHeader("Content-Encoding");
          if (enc != null && "gzip".equalsIgnoreCase(enc.getValue())) {
            GZIPInputStream gzipInput = new GZIPInputStream(respStream);
            transferStream(gzipInput, new FileOutputStream(mirror), new GZIPOutputStream(resp.getOutputStream()));
          } else {
            transferStream(respStream, new FileOutputStream(mirror), new GZIPOutputStream(resp.getOutputStream()));
          }
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

    File dataFolder = new File("data");

    if (!dataFolder.exists()) {
      dataFolder.mkdirs();
    }

    dataLocation = dataFolder.getAbsolutePath();
    if (!dataLocation.endsWith("/")) {
      dataLocation += "/";
    }

    clients = new HashMap<String, HttpClient>();
    localAddresses = new HashSet<String>();
    Enumeration allNetInterfaces = null;
    try {
      allNetInterfaces = NetworkInterface.getNetworkInterfaces();
    } catch (java.net.SocketException e) {
      e.printStackTrace();
    }
    InetAddress ip = null;
    while (allNetInterfaces.hasMoreElements()) {
      NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
      logger.info(netInterface.getName());
      Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
      while (addresses.hasMoreElements()) {
        ip = addresses.nextElement();
        if (ip != null && ip instanceof Inet4Address) {
          logger.info(ip.getHostName());
          logger.info(ip.getHostAddress());
          localAddresses.add(ip.getHostAddress());
        }
      }
    }
  }

  private File getMirror(String host, String uri, String query) {
    int index = uri.lastIndexOf("/");
    String ending = index >= 0 ? uri.substring(index + 1) : uri;
    index = ending.lastIndexOf(".");
    if (index < 0) {
      uri += ".";
      if (query != null) {
        uri += query;
        uri += ".";
      }
      uri += "json";
    }
    return new File(dataLocation + host + uri + (uri.endsWith("/") ? "index.html" : ""));
  }

}
