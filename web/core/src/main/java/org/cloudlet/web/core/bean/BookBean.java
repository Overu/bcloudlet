package org.cloudlet.web.core.bean;

import org.cloudlet.web.core.service.BookService;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = BookBean.TYPE_NAME)
@XmlType(name = BookBean.TYPE_NAME)
@Entity
@Table(name = "book")
@Path("book")
@DefaultField(key = "title", value = "图书")
@Handler(BookService.class)
public class BookBean extends ResourceBean {

  @SuppressWarnings("hiding")
  public static final String TYPE_NAME = "Book";

  public static final String SECTIONS = "sections";

  @OneToOne
  private MediaBean cover;

  @SuppressWarnings("hiding")
  public static ResourceType<BookBean> TYPE = new ResourceType<BookBean>(ResourceBean.TYPE,
      TYPE_NAME) {
    @Override
    public BookBean createInstance() {
      return new BookBean();
    }
  };

  @Path("cover")
  @DefaultField(key = "title", value = "成员")
  @XmlTransient
  public MediaBean getCover() {
    return cover;
  }

  @Override
  public ResourceType<BookBean> getResourceType() {
    return TYPE;
  }

  public void setCover(MediaBean cover) {
    this.cover = cover;
  }

}
