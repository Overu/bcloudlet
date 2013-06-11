package org.cloudlet.core.servlet.tags;

import org.cloudlet.core.server.Content;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class IncludeTag extends TagSupport {

  private Content content;

  private String page;

  private boolean flush;

  @Override
  public int doEndTag() throws JspException {
    return super.doEndTag();
  }

  @Override
  public int doStartTag() throws JspException {
    try {
      pageContext.include(page, flush);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return EVAL_BODY_INCLUDE;
  }

  public String getPage() {
    return page;
  }

  public boolean isFlush() {
    return flush;
  }

  @Override
  public void release() {
    super.release();
    content = null;
  }

  public void setFlush(boolean flush) {
    this.flush = flush;
  }

  public void setPage(String page) {
    this.page = page;
  }

}