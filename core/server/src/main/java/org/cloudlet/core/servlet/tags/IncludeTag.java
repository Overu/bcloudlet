package org.cloudlet.core.servlet.tags;

import org.cloudlet.core.server.Content;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class IncludeTag extends TagSupport {

  private Content content;

  private String page;

  private boolean flush;

  private boolean parent;

  @Override
  public int doEndTag() throws JspException {
    if (parent) {
      pageContext.getRequest().setAttribute("it", content);
    }
    return super.doEndTag();

  }

  @Override
  public int doStartTag() throws JspException {
    try {
      if (parent) {
        content = (Content) pageContext.getRequest().getAttribute("it");
        if (content != null) {
          Content parent = content.getParent();
          if (parent != null) {
            pageContext.getRequest().setAttribute("it", parent);
            String absolutePage = "/jsp/" + parent.getClass().getName().replaceAll("\\.", "/") + "/" + page;
            pageContext.include(absolutePage, flush);
          }
        }
      } else {
        pageContext.include(page, flush);
      }
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

  public boolean isParent() {
    return parent;
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

  public void setParent(boolean parent) {
    this.parent = parent;
  }

}