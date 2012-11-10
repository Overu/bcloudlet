package org.cloudlet.web.core.shared;

import com.google.gwt.core.shared.GWT;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@MappedSuperclass
@XmlType
public abstract class Entry extends Content {

  public static EntryType<Entry> TYPE = new EntryType<Entry>(Content.TYPE, "content");

  public static final String RELATIONSHIPS = "relationships";

  @Transient
  private List<Content> relationships;
  @Transient
  @QueryParam(RELATIONSHIPS)
  protected boolean loadRelationships;

  @POST
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public DataGraph<Content> create(DataGraph<Content> data) {
    data.root = createRelationship(data.root);
    return data;
  }

  public Content createRelationship(Content child) {
    return getService().createRelationship(this, child);
  }

  @Override
  public EntryType getObjectType() {
    return TYPE;
  }

  @Path("{path}")
  public <T extends Content> T getRelationship(@PathParam("path") String path) {
    Content result = getCache().get(path);
    if (result == null) {
      if (GWT.isClient()) {
        result = new ContentProxy();
        result.setPath(path);
        result.setParent(this);
      } else {
        result = getService().getRelationship(this, path);
      }
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
  public List<Content> getRelationships() {
    return relationships;
  }

  @Override
  public EntryService getService() {
    return (EntryService) super.getService();
  }

  public void loadRelationships() {
    if (loadRelationships) {
      relationships = getService().findRelationships(this);
    }
  }

  @Override
  protected void doLoad() {
    super.doLoad();
    loadRelationships();
  }
}
