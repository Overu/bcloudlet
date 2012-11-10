package org.cloudlet.web.core.shared;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Path("t_view")
public class View extends Content {

  public static final ObjectType TYPE = new ObjectType(Content.TYPE, "view");
  public static final String HOME = "";
  public static final String FOLDER = "/";
  public static final String POST = "action=create";

  @Override
  public View getDefaultView() {
    return this;
  }

  @Override
  public ObjectType getObjectType() {
    return TYPE;
  }

  @Override
  public StringBuilder getUriBuilder() {
    StringBuilder result = getParent().getUriBuilder();
    if (path.length() > 0) {
      result.append("?").append(path);
    }
    return result;
  }

}
