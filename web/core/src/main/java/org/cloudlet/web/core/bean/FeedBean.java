package org.cloudlet.web.core.bean;

import com.google.gwt.core.shared.GWT;

import com.sencha.gxt.data.shared.SortInfo;

import org.cloudlet.web.core.service.FeedService;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@MappedSuperclass
@XmlType(name = FeedBean.TYPE_NAME)
public abstract class FeedBean<E extends ResourceBean> extends ResourceBean {

  public static final String TYPE_NAME = "Feed";

  public static FeedType TYPE = new FeedType(ResourceBean.TYPE, TYPE_NAME, ResourceBean.TYPE);

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
  public FeedType<? extends FeedBean<?>, ? extends E> getResourceType() {
    return TYPE;
  }

  @Override
  public FeedService getService() {
    return (FeedService) super.getService();
  }

  public E newEntry() {
    return getResourceType().getEntryType().createInstance();
  }

  public void setEntries(List<E> entries) {
    this.entries = entries;
  }

  @Override
  protected ResourceBean doGetByPath(String path) {
    ResourceBean result = getEntry(path);
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
