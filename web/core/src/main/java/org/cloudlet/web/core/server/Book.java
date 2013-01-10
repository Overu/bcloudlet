package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.Book)
@XmlType(name = CorePackage.Book)
@Entity(name = CorePackage.Book)
@Table(name = CorePackage.Book)
@DefaultField(key = "title", value = "图书")
public class Book extends Resource {

  @OneToOne
  private Media cover;

  @OneToOne
  private Media source;

  @Path("cover")
  public Media getCover() {
    return cover;
  }

  @Override
  public String getResourceType() {
    return CorePackage.Book;
  }

  @Path("source")
  public Media getSource() {
    return source;
  }

  @Override
  public void readMedia(MultivaluedMap<String, Media> params) {
    super.readMedia(params);
    Media cover = params.getFirst(CorePackage.COVER);
    if (cover != null) {
      this.cover = cover;
      cover.setParent(this);
    }
    Media source = params.getFirst(CorePackage.SOURCE);
    if (source != null) {
      this.source = source;
      source.setParent(this);
    }
  }

  public void setCover(Media cover) {
    if (cover != null) {
      cover.setParent(this);
    }
    this.cover = cover;
  }

  public void setSource(Media source) {
    if (source != null) {
      source.setParent(this);
    }
    this.source = source;
  }

}
