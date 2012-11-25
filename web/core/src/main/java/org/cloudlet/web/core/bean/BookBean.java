package org.cloudlet.web.core.bean;

import org.cloudlet.web.core.service.BookService;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity
@Path("book")
@DefaultField(key = "title", value = "图书")
@Handler(BookService.class)
public class BookBean extends ResourceBean {

  public static final String SECTIONS = "sections";

  @OneToOne
  private MediaBean cover;

  @Path("cover")
  @DefaultField(key = "title", value = "成员")
  @XmlTransient
  public MediaBean getCover() {
    return cover;
  }

  public void setCover(MediaBean cover) {
    this.cover = cover;
  }

}
