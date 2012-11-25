package org.cloudlet.web.core.bean;

import org.cloudlet.web.core.service.MediaService;
import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlType;

@XmlType
@Entity(name = MediaBean.TYPE_NAME)
@Table(name = CorePackage.PREFIX + MediaBean.TYPE_NAME)
@Path("media")
@Handler(MediaService.class)
public class MediaBean extends ResourceBean {

  public static final String TYPE_NAME = "Media";

  public static final ResourceType TYPE = new ResourceType(ResourceBean.TYPE, TYPE_NAME);

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
