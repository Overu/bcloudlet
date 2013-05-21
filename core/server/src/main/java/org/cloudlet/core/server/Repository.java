package org.cloudlet.core.server;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.glassfish.jersey.server.mvc.Viewable;

import java.net.URI;
import java.net.URISyntaxException;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
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

  public static final String LOGIN = "login";

  public static final String LOGOUT = "logout";

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

  @POST
  @Path(LOGIN)
  public Response login(@FormParam("username") String username, @FormParam("password") String password, @FormParam("forward") String forward) {
    Subject subject = SecurityUtils.getSubject();
    AuthenticationToken token = new UsernamePasswordToken(username, password);
    subject.login(token);
    return redirect(forward);
  }

  @GET
  @Path(LOGIN)
  public Response loginCheck(@QueryParam("forward") String forward) {
    Subject subject = SecurityUtils.getSubject();
    if (subject.isAuthenticated()) {
      User user = (User) subject.getPrincipal();
      return redirect(forward);
    } else {
      return Response.ok(new Viewable(LOGIN)).build();
    }
  }

  @GET
  @Path(LOGOUT)
  public Response logout(@QueryParam("forward") String forward) {
    Subject subject = SecurityUtils.getSubject();
    subject.logout();
    return redirect(forward);
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

  private Response redirect(String forward) {
    URI uri = null;
    try {
      uri = forward == null ? uriInfo.getBaseUri() : new URI(forward);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return Response.status(Status.FOUND).location(uri).build();
  }
}
