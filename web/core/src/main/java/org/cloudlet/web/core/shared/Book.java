package org.cloudlet.web.core.shared;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = Book.TYPE_NAME)
@XmlType(name = Book.TYPE_NAME)
@Entity
@Table(name = "book")
@Handler(BookService.class)
@Path("book")
@DefaultField(key = "title", value = "图书")
public class Book extends Resource {

  @SuppressWarnings("hiding")
  public static final String TYPE_NAME = "Book";

  public static final String SECTIONS = "sections";

  @OneToOne
  private Media cover;

  @SuppressWarnings("hiding")
  public static ResourceType<Book> TYPE = new ResourceType<Book>(Resource.TYPE, TYPE_NAME) {
    @Override
    public Book createInstance() {
      return new Book();
    }
  };

  @Path("cover")
  @DefaultField(key = "title", value = "成员")
  @XmlTransient
  public Media getCover() {
    return cover;
  }

  @Override
  public ResourceType<Book> getResourceType() {
    return TYPE;
  }

  public void setCover(Media cover) {
    this.cover = cover;
  }

}
