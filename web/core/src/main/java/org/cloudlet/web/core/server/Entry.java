package org.cloudlet.web.core.server;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.ws.rs.DELETE;
import javax.ws.rs.QueryParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@MappedSuperclass
@EntityListeners(InjectionListener.class)
public abstract class Entry extends Content {

  private static final Logger logger = Logger.getLogger(Entry.class.getName());

  public static final String REFERENCES = "children";

  @Transient
  @QueryParam(Entry.REFERENCES)
  protected boolean loadReferences;

  @Transient
  protected List<? extends Content> references;

  protected long total;

  @Transient
  protected long count;

  public Content createReference(Content target) {
    getService().createReference(this, target);
    return target;
  }

  @Override
  @SuppressWarnings("unchecked")
  @DELETE
  public void delete() {
    getService().delete(this);
  }

  public long getCount() {
    return count;
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

  public long getTotal() {
    return total;
  }

  public void setCount(long count) {
    this.count = count;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public void setReferences(List<? extends Content> contents) {
    this.references = contents;
  }

  public void setTotal(long total) {
    this.total = total;
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
      count = getService().countReferences(this);
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
