package org.cloudlet.web.core;

import org.cloudlet.web.core.service.EntryService;
import org.cloudlet.web.core.shared.CorePackage;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@MappedSuperclass
@XmlType
public abstract class Entry extends Content {

  @Transient
  private List<Content> children;

  @Transient
  @QueryParam(CorePackage.Content.CHILDREN)
  protected boolean loadChildren;

  @Override
  public Content createChild(Content child) {
    return getService().createChild(this, child);
  }

  @Path("{path}")
  public final <T extends Content> T getChild(@PathParam("path") String path) {
    Content result = getCache().get(path);
    if (result == null) {
      result = getService().findChild(this, path);
      if (result != null) {
        getCache().put(path, result);
      }
    }
    if (result != null && resourceContext != null) {
      resourceContext.initResource(result);
    }
    return (T) result;
  }

  @XmlElement
  public List<Content> getChildren() {
    return children;
  }

  @Override
  public EntryService getService() {
    return (EntryService) super.getService();
  }

  public void loadChildren() {
    if (loadChildren) {
      children = getService().findChildren(this);
    }
  }

  @Override
  protected void doLoad() {
    super.doLoad();
    loadChildren();
  }
}
