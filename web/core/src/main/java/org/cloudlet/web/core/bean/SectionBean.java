package org.cloudlet.web.core.bean;

import org.cloudlet.web.core.service.SectionService;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = SectionBean.TYPE_NAME)
@Entity
@Table(name = "t_section")
@Handler(SectionService.class)
@Path("section")
@DefaultField(key = "title", value = "用户组")
public class SectionBean extends ResourceBean {

  public static final String TYPE_NAME = "Section";

  public static ResourceType TYPE = new ResourceType(ResourceBean.TYPE, TYPE_NAME) {
    @Override
    public SectionBean createInstance() {
      return new SectionBean();
    }
  };

  @Override
  public ResourceType getResourceType() {
    return TYPE;
  }

}
