package org.cloudlet.web.core.shared;

import com.google.gwt.core.shared.GWT;

import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.SortInfoBean;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@MappedSuperclass
@XmlType(name = Feed.TYPE_NAME)
public abstract class Feed<E extends Resource> extends Resource implements ListLoadConfig {

  public static final String TYPE_NAME = "Feed";

  public static FeedType TYPE = new FeedType(Resource.TYPE, TYPE_NAME, Resource.TYPE);

  public static final String SORT = "sort";

  @Transient
  protected List<E> entries;

  @Transient
  protected List<? extends SortInfo> sortInfo;

  @Transient
  protected Long queryCount;

  public static final String NEW = "new";

  public static final String LIST = "list";

  public static final String ENTRIES = "entries";

  public E createEntry(E entry) {
    return (E) getService().createEntry(this, entry);
  }

  @XmlElement
  public List<E> getEntries() {
    return entries;
  }

  public E getEntry(String path) {
    E result = null;
    if (GWT.isClient()) {
      if (entries != null) {
        for (E entry : entries) {
          if (path.equals(entry.getPath())) {
            result = entry;
          }
        }
      }
    } else {
      result = (E) getService().findEntry(this, path);
    }
    if (result != null && resourceContext != null) {
      resourceContext.initResource(result);
    }
    return result;
  }

  @XmlTransient
  public abstract Class<E> getEntryType();

  @Override
  public Object getPropertyValue(String path) {
    return super.getPropertyValue(path);
  }

  @XmlElement
  public Long getQueryCount() {
    return queryCount;
  }

  @Override
  public FeedType<? extends Feed<?>, ? extends E> getResourceType() {
    return TYPE;
  }

  @Override
  public FeedService getService() {
    return (FeedService) super.getService();
  }

  @Override
  @XmlTransient
  public List<? extends SortInfo> getSortInfo() {
    List<String> sorts = getQueryParameters().get(Feed.SORT);
    if (sorts != null) {
      List<SortInfo> sortInfo = new ArrayList<SortInfo>();
      for (String sort : sorts) {
        String[] pair = sort.split("|");
        SortInfoBean s = new SortInfoBean();
        s.setSortField(pair[0]);
        s.setSortDir(pair.length > 1 ? SortDir.valueOf(pair[1]) : SortDir.ASC);
        sortInfo.add(s);
      }
      return sortInfo;
    }
    return Collections.EMPTY_LIST;
  }

  public E newEntry() {
    return getResourceType().getEntryType().createInstance();
  }

  public void setEntries(List<E> entries) {
    this.entries = entries;
  }

  @Override
  public void setSortInfo(List<? extends SortInfo> info) {
    for (SortInfo sort : info) {
      String value = sort.getSortField() + "|" + sort.getSortDir();
      getQueryParameters().add(Feed.SORT, value);
    }
  }

  @Override
  protected Resource doGetByPath(String path) {
    Resource result = getEntry(path);
    if (result == null) {
      result = super.doGetByPath(path);
    }
    return result;
  }

  @Override
  protected void doLoad() {
    super.doLoad();
    if (NEW.equals(renditionKind)) {
      entries = new ArrayList<E>();
      E entry = newEntry();
      entries.add(entry);
    } else if (LIST.equals(renditionKind)) {
      doLoadEntries();
    } else {
      doLoadEntries();
    }
  }

  protected void doLoadEntries() {
    entries = getService().findEntries(this, 0, -1);
    queryCount = getService().countEntries(this);
  }

}
