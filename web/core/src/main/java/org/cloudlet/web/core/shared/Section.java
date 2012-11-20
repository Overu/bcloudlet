package org.cloudlet.web.core.shared;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = Section.TYPE_NAME)
@Entity
@Table(name = "t_section")
@Handler(SectionService.class)
@Path("section")
@DefaultField(key = "title", value = "用户组")
public class Section extends Resource {

  public static final String TYPE_NAME = "Section";

  public static ResourceType TYPE = new ResourceType(Resource.TYPE, TYPE_NAME) {
    @Override
    public Section createInstance() {
      return new Section();
    }
  };

  @Override
  public ResourceType getResourceType() {
    return TYPE;
  }

}
