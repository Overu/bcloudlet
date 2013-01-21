package org.cloudlet.web.core.servlet;

import com.google.inject.Singleton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public class MirrorFilter implements Filter {

  public static void readBytes(InputStream is, OutputStream... outs) throws IOException {
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

  private Site site;

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;
    if (site != null) {
      String uri = req.getRequestURI();
      File localFile = new File(site.getMirror() + uri + (uri.endsWith("/") ? "index.html" : ""));
      if (!localFile.exists()) {
        File parent = localFile.getParentFile();
        if (!parent.exists()) {
          parent.mkdirs();
        }
        String remoteUrl = site.getUrl() + uri;
        readBytes(new URL(remoteUrl).openStream(), new FileOutputStream(localFile), resp.getOutputStream());
      } else {
        readBytes(new FileInputStream(localFile), resp.getOutputStream());
      }
    } else {
      chain.doFilter(request, response);
    }
  }

  @Override
  public void init(FilterConfig config) throws ServletException {
    String url = "http://tukeq.com/";
    String id = "tukeq";
    site = new Site(id, url, "D:/Code/cloudlet/web/app/mirrors/" + id);
  }
}
