package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;
import org.hibernate.annotations.TypeDef;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.ws.rs.DELETE;
import javax.ws.rs.QueryParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@TypeDef(name = "resource", typeClass = ResourceType.class)
@MappedSuperclass
@EntityListeners(InjectionListener.class)
public abstract class Entry extends Content {

  private static final Logger logger = Logger.getLogger(Entry.class.getName());

  @Transient
  @QueryParam(CorePackage.REFERENCES)
  protected boolean loadReferences;

  protected long totalReferences;

  @Transient
  protected List<? extends Content> references;

  public Content createReference(Content child) {
    getService().createReference(this, child);
    return child;
  }

  @Override
  @SuppressWarnings("unchecked")
  @DELETE
  public void delete() {
    getService().delete(this);
  }

  @XmlElement
  public List<? extends Content> getReferences() {
    return references;
  }

  @Override
  public EntryService getService() {
    return (EntryService) super.getService();
  }

  @Override
  @XmlTransient
  public Class<? extends Service> getServiceType() {
    return EntryService.class;
  }

  public long getTotalReferences() {
    return totalReferences;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public void setReferences(List<? extends Content> children) {
    this.references = children;
  }

  public void setTotalReferences(long totalReferences) {
    this.totalReferences = totalReferences;
  }

  @Override
  public Content update() {
    getService().update(this);
    return this;
  }

  @Override
  protected void doLoad() {
    if (loadReferences) {
      references = getService().findReferences(this);
      totalReferences = getService().countReferences(this);
    }
  }

  @Override
  protected Content findChild(String path) {
    return getService().getReference(this, path);
  }

  @Override
  protected void init() {
    getService().init(this);
  }
}
