package org.cloudlet.web.core.service;

import org.cloudlet.web.core.Book;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = Book.TYPE)
@XmlType(name = Book.TYPE)
@Entity(name = Book.TYPE)
@Table(name = Book.TYPE)
@DefaultField(key = "title", value = "图书")
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

  @Override
  public String getResourceType() {
    return Book.TYPE;
  }

  public void setCover(MediaBean cover) {
    this.cover = cover;
  }

}
