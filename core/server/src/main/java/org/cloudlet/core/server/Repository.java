package org.cloudlet.core.server;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Repository.TYPE_NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Path("/")
public class Repository extends Item {

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Repository";

  public static final String USERS = "users";

  public static final String GROUPS = "groups";

  public static final String TAGS = "tags";

  @OneToOne
  private Users users;

  @OneToOne
  private Groups groups;

  @OneToOne
  private Tags tags;

  @Context
  private transient HttpServletRequest request;

  @Context
  @Transient
  private HttpServletResponse response;

  @Override
  public void doLoad() {
    super.doLoad();
    tags.doLoad();
  }

  @Path(GROUPS)
  public Groups getGroups() {
    return groups;
  }

  @Path(TAGS)
  public Tags getTags() {
    return tags;
  }

  @Override
  @XmlTransient
  public UriBuilder getUriBuilder() {
    if (uriInfo == null) {
      RuntimeDelegate rd = RuntimeDelegate.getInstance();
      return rd.createUriBuilder();
    }
    return uriInfo.getBaseUriBuilder();
  }

  @Path(USERS)
  public Users getUsers() {
    return users;
  }

  public final void save() {
    WebPlatform.get().getInstance(ContentService.class).save(this);
  }

  public void setGroups(Groups groups) {
    this.groups = groups;
  }

  public void setTags(Tags tags) {
    this.tags = tags;
  }

  public void setUsers(Users users) {
    this.users = users;
  }

  @Override
  protected boolean doInit() {
    super.doInit();
    users = createChild(USERS, Users.class);
    tags = createChild(TAGS, Tags.class);
    return true;
  }

  @Override
  protected void initResource(Object result) {
    if (result != null) {
      if (resourceContext != null) {
        resourceContext.initResource(result);
      }
    }
  }
}
