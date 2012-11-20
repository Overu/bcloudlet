package org.cloudlet.web.core.shared;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlType;

@XmlType
@Entity(name = Media.TYPE_NAME)
@Table(name = CorePackage.PREFIX + Media.TYPE_NAME)
@Path("media")
@Handler(MediaService.class)
public class Media extends Resource {

  public static final String TYPE_NAME = "Media";

  public static final ResourceType TYPE = new ResourceType(Resource.TYPE, TYPE_NAME);

  @Override
  public ResourceType getResourceType() {
    return TYPE;
  }

  @Override
  public Object getWidget() {
    if (widget == null) {
      widget = getParent().getResourceType().getWidget(getPath());
    }
    return widget;
  }
}
