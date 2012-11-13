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
public class Section extends Entry {

  public static final String TYPE_NAME = "Section";

  public static EntryType TYPE = new EntryType(Entry.TYPE, TYPE_NAME) {
    @Override
    public Entry createInstance() {
      return new Section();
    }
  };

  @Override
  public EntryType getResourceType() {
    return TYPE;
  }

}
