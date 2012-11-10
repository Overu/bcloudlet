package org.cloudlet.web.core.shared;

// import com.google.gwt.json.client.JSONObject;
// import com.google.gwt.json.client.JSONValue;
import com.google.gwt.place.shared.Place;

import org.cloudlet.web.core.server.ContentUserType;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@TypeDef(name = "content", typeClass = ContentUserType.class)
@MappedSuperclass
@XmlType
public abstract class Content extends Place {

  private static final Logger logger = Logger.getLogger(Content.class.getName());

  public static ObjectType TYPE = new ObjectType("content");

  public static final String ID = "id";

  public static final String TITLE = "title";

  public static final String PATH = "path";

  public static final String TOTAL_COUNT = "totalCount";

  public static final String VERSION = "version";

  protected String title;

  protected long totalCount;

  @Transient
  private transient Object widget;

  @Context
  @Transient
  ResourceContext resourceContext;

  @Id
  protected String id;

  protected String path;

  @Version
  protected long version;

  @ManyToOne
  protected User owner;

  @Type(type = "content")
  @Columns(columns = {@Column(name = "parentType"), @Column(name = "parentId")})
  private Content parent;

  @Transient
  private Map<String, Content> cache;

  @Transient
  private transient Object nativeData;

  @DELETE
  public void delete() {
    getService().delete(this);
  }

  public Content findChild(final String uriWithQueryString) {
    String[] str = uriWithQueryString.split("\\?");
    String[] segments = str[0].split("/");
    String queryStr = str.length > 1 ? str[1] : null;

    Content result = this;
    for (String path : segments) {
      if (path.length() == 0) {
        continue;
      }
      if (result instanceof Entry) {
        result = ((Entry) result).getRelationship(path);
      } else if (result instanceof Feed) {
        result = ((Feed) result).getEntry(path);
      } else {
        logger.severe("No content found for " + path);
        break;
      }
    }
    if (result != null && queryStr != null) {
      result = result.getView(queryStr);
    }
    return result;
  }

  public Map<String, Content> getCache() {
    if (cache == null) {
      cache = new HashMap<String, Content>();
    }
    return cache;
  }

  public View getDefaultView() {
    return getView(View.HOME);
  }

  public String getId() {
    return id;
  }

  public Object getNativeData() {
    return nativeData;
  }

  @XmlTransient
  public ObjectType getObjectType() {
    return TYPE;
  }

  public User getOwner() {
    return owner;
  }

  @XmlTransient
  public Content getParent() {
    return parent;
  }

  public String getPath() {
    return path;
  }

  public Object getProperty(String name) {
    if (TITLE.equals(name)) {
      return title;
    }
    if (PATH.equals(name)) {
      return path;
    }
    return null;
  }

  public Service getService() {
    return WebPlatform.getInstance().getService(getClass());
  }

  public String getTitle() {
    return title;
  }

  public long getTotalCount() {
    return totalCount;
  }

  @XmlElement
  public String getUri() {
    return getUriBuilder().toString();
  }

  public StringBuilder getUriBuilder() {
    if (parent == null) {
      return new StringBuilder("/");
    }
    StringBuilder builder = parent.getUriBuilder();
    builder.append(path).append("/");
    return builder;
  }

  public long getVersion() {
    return version;
  }

  // public abstract boolean isLoaded();

  public View getView(String kind) {
    View result = (View) getCache().get(kind);
    if (result == null) {
      Object widget = getObjectType().getWidget(kind);
      if (widget != null) {
        result = new View();
        result.setParent(this);
        result.setPath(kind);
        getCache().put(kind, result);
      }
    }
    return result;
  }

  @XmlTransient
  public Object getWidget() {
    return widget;
  }

  // public void readFrom(JSONObject json) {
  // id = readString(json, ID);
  // title = readString(json, TITLE);
  // path = readString(json, PATH);
  // version = readLong(json, VERSION);
  // }

  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public DataGraph<Content> load() {
    doLoad();
    DataGraph<Content> data = new DataGraph<Content>();
    data.root = this;
    return data;
  }

  public void readFrom(Content delta) {
    if (delta.title != null) {
      this.title = delta.title;
    }
    if (delta.path != null) {
      this.path = delta.path;
    }
  }

  public Content save() {
    return getService().save(this);
  }

  public void setId(final String id) {
    this.id = id;
  }

  public void setNativeData(Object nativeData) {
    this.nativeData = nativeData;
  }

  public void setOwner(final User owner) {
    this.owner = owner;
  }

  public void setParent(final Content parent) {
    this.parent = parent;
  }

  public void setPath(final String path) {
    this.path = path;
  }

  public void setProperty(String name, String value) {
    if (TITLE.equals(name)) {
      title = value;
    } else if (PATH.equals(name)) {
      path = value;
    }
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setTotalCount(long totalResults) {
    this.totalCount = totalResults;
  }

  public void setVersion(long version) {
    this.version = version;
  }

  public void setWidget(Object widget) {
    this.widget = widget;
  }

  public Content update() {
    return getService().update(this);
  }

  @PUT
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public DataGraph<Content> update(DataGraph<Content> data) {
    readFrom(data.root);
    update();
    data.root = this;
    return data;
  }

  protected void doLoad() {
  }

  // protected boolean readBoolean(JSONObject json, String field) {
  // Boolean b = readBooleanObject(json, field);
  // return b == null ? false : b.booleanValue();
  // }
  //
  // protected Boolean readBooleanObject(JSONObject json, String field) {
  // JSONValue value = json.get(field);
  // if (value != null) {
  // return value.isBoolean().booleanValue();
  // }
  // return null;
  // }
  //
  // protected Date readDate(JSONObject json, String field) {
  // Double d = readDoubleObject(json, field);
  // if (d != null) {
  // return new Date(d.longValue());
  // }
  // return null;
  // }
  //
  // protected double readDouble(JSONObject json, String field) {
  // Double d = readDoubleObject(json, field);
  // if (d != null) {
  // return d.doubleValue();
  // }
  // return 0;
  // }
  //
  // protected Double readDoubleObject(JSONObject json, String field) {
  // JSONValue value = json.get(field);
  // if (value != null) {
  // return value.isNumber().doubleValue();
  // }
  // return null;
  // }
  //
  // protected int readInt(JSONObject json, String field) {
  // Double d = readDoubleObject(json, field);
  // if (d != null) {
  // return d.intValue();
  // }
  // return 0;
  // }
  //
  // protected Integer readInteger(JSONObject json, String field) {
  // Double d = readDoubleObject(json, field);
  // if (d != null) {
  // return d.intValue();
  // }
  // return null;
  // }
  //
  // protected long readLong(JSONObject json, String field) {
  // Double d = readDoubleObject(json, field);
  // if (d != null) {
  // return d.longValue();
  // }
  // return 0;
  // }
  //
  // protected Long readLongObject(JSONObject json, String field) {
  // Double d = readDoubleObject(json, field);
  // if (d != null) {
  // return d.longValue();
  // }
  // return null;
  // }
  //
  // protected String readString(JSONObject json, String field) {
  // JSONValue value = json.get(field);
  // if (value != null) {
  // return value.isString().stringValue();
  // }
  // return null;
  // }
}
