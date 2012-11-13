package org.cloudlet.web.core.shared;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = Book.TYPE_NAME)
@XmlType(name = Book.TYPE_NAME)
@Entity
@Table(name = "book")
@Handler(BookService.class)
@Path("book")
@DefaultField(key = "title", value = "图书")
public class Book extends Entry {

  public static final String TYPE_NAME = "Book";

  public static final String SECTIONS = "sections";

  @Transient
  private List<Section> sections;

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

  public List<Section> getSections() {
    if (sections == null) {
      sections = getChildren(Section.class);
    }
    return sections;
  }

}
