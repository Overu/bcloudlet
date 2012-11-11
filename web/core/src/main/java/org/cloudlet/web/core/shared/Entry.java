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
@XmlType(name = Entry.TYPE_NAME)
public abstract class Entry extends Content {

  public static final String TYPE_NAME = "Entry";

  public static EntryType<Entry> TYPE = new EntryType<Entry>(Content.TYPE, TYPE_NAME);

  public static final String RELATIONSHIPS = "relationships";

  @Transient
  private List<Resource> relationships;

  @Transient
  @QueryParam(RELATIONSHIPS)
  protected boolean loadRelationships;

  @POST
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public DataGraph<Resource> create(DataGraph<Resource> data) {
    data.root = createRelationship(data.root);
    return data;
  }

  public Resource createRelationship(Resource child) {
    return getService().createRelationship(this, child);
  }

  @Path("{path}")
  public <T extends Resource> T getRelationship(@PathParam("path") String path) {
    Resource result = getCache().get(path);
    if (result == null && !GWT.isClient()) {
      result = getService().getRelationship(this, path);
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
  public List<Resource> getRelationships() {
    return relationships;
  }

  @Override
  public EntryType<?> getResourceType() {
    return TYPE;
  }

  @Override
  public EntryService getService() {
    return (EntryService) super.getService();
  }

  public void loadRelationships() {
    relationships = getService().findRelationships(this);
  }

  @Override
  protected void doLoad() {
    super.doLoad();
    if (loadRelationships) {
      loadRelationships();
    }
  }
}
