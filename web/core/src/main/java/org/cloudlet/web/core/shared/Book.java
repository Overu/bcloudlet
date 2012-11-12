package org.cloudlet.web.core.shared;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = Book.TYPE_NAME)
@Entity
@Table(name = "book")
@Handler(GroupService.class)
@Path("book")
@DefaultField(key = "title", value = "用户组")
public class Book extends Entry {
  public static final String TYPE_NAME = "Book";

  public static EntryType TYPE = new EntryType(Entry.TYPE, TYPE_NAME) {
    @Override
    public Entry createInstance() {
      return new Book();
    }
  };

  @Override
  public EntryType getResourceType() {
    return TYPE;
  }

  @Path("sections")
  @DefaultField(key = "title", value = "用户组")
  @XmlTransient
  public SectionFeed getSections() {
    return (SectionFeed) getRelationship("sections");
  }

}
