package org.cloudlet.core.servlet;

import com.google.inject.Singleton;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Singleton
public class CharacterEncodingFilter implements Filter {

  private String encoding = "UTF-8";
  private boolean forceEncoding = true;

  @Override
  public void destroy() {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    if (this.forceEncoding || request.getCharacterEncoding() == null) {
      request.setCharacterEncoding(this.encoding);
      response.setCharacterEncoding(this.encoding);
    }
    chain.doFilter(request, response);
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    this.encoding = filterConfig.getInitParameter("encoding");
    String force = filterConfig.getInitParameter("forceEncoding");
    this.forceEncoding = (force == null) || Boolean.valueOf(force).booleanValue();
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  public void setForceEncoding(boolean forceEncoding) {
    this.forceEncoding = forceEncoding;
  }

}
