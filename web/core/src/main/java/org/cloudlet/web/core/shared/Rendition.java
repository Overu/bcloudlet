package org.cloudlet.web.core.shared;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlType;

@XmlType
@Entity(name = Rendition.TYPE_NAME)
@Table(name = CorePackage.PREFIX + Rendition.TYPE_NAME)
@Path("rendition")
@Handler(RenditionService.class)
public class Rendition extends Resource {

  public static final String TYPE_NAME = "Rendition";

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
