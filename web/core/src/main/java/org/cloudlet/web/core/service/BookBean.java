package org.cloudlet.web.core.service;

import org.cloudlet.web.core.Book;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = Book.TYPE)
@XmlType(name = Book.TYPE)
@Entity(name = Book.TYPE)
@Table(name = Book.TYPE)
@DefaultField(key = "title", value = "图书")
public class BookBean extends ResourceBean {

  @OneToOne
  private MediaBean cover;

  @OneToOne
  private MediaBean source;

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

  public MediaBean getSource() {
    return source;
  }

  @Override
  public void readMedia(MultivaluedMap<String, MediaBean> params) {
    super.readMedia(params);
    MediaBean cover = params.getFirst(Book.COVER);
    if (cover != null) {
      this.cover = cover;
      cover.setParent(this);
    }
    MediaBean source = params.getFirst(Book.SOURCE);
    if (source != null) {
      this.source = source;
      source.setParent(this);
    }
  }

  @Override
  public ResourceBean save() {
    super.save();
    if (cover != null) {
      cover.save();
    }
    if (source != null) {
      source.save();
    }
    return this;
  }

  public void setCover(MediaBean cover) {
    this.cover = cover;
  }

  public void setSource(MediaBean source) {
    this.source = source;
  }

}
