package org.cloudlet.web.core.server;

import org.hibernate.annotations.TypeDef;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.ws.rs.DELETE;
import javax.xml.bind.annotation.XmlTransient;

@TypeDef(name = "resource", typeClass = ResourceType.class)
@MappedSuperclass
@EntityListeners(InjectionListener.class)
public abstract class Entry extends Content {

  private static final Logger logger = Logger.getLogger(Entry.class.getName());

  public Content createReference(Content child) {
    getService().createReference(this, child);
    return child;
  }

  @Override
  @SuppressWarnings("unchecked")
  @DELETE
  public void delete() {
    getService().deleteEntry(this);
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

  @Override
  public Content update() {
    getService().update(this);
    return this;
  }

  @Override
  protected List<? extends Content> doLoad() {
    return getService().findReferences(this);
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
