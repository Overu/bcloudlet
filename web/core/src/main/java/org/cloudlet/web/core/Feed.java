package org.cloudlet.web.core;

import org.cloudlet.web.core.service.FeedService;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlType;

@MappedSuperclass
@XmlType
public abstract class Feed<E extends Entry> extends Content {

  protected long totalResults;

  @Transient
  protected List<E> entries;

  public List<E> getEntries() {
    return entries;
  }

  public abstract Class<E> getEntryType();

  @Override
  public FeedService getService() {
    return (FeedService) super.getService();
  }

  public long getTotalResults() {
    return totalResults;
  }

  // @Override
  // @GET
  // @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  // public Content load() {
  // loadBasicInfo();
  // entries = getService().findChildren(this, 0, -1, getEntryType());
  // return this;
  // }

  public void setEntries(List<E> entries) {
    this.entries = entries;
  }

  public void setTotalResults(long totalResults) {
    this.totalResults = totalResults;
  }

  @Override
  protected void loadBasicInfo() {
    super.loadBasicInfo();
    entries = getService().findChildren(this, 0, -1, getEntryType());
  }

  @Override
  protected Content lookupChild(String path) {
    Content result = super.lookupChild(path);
    if (result == null) {
      Class<E> entryType = getEntryType();
      result = getService().getChild(this, path, entryType);
    }
    return result;
  }

}
