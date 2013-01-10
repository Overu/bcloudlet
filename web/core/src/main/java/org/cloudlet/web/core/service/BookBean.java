package org.cloudlet.web.core.service;

import org.cloudlet.web.core.CorePackage;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = BookBean.TYPE)
@XmlType(name = BookBean.TYPE)
@Entity(name = BookBean.TYPE)
@Table(name = BookBean.TYPE)
@DefaultField(key = "title", value = "图书")
public class BookBean extends ResourceBean {
  public static final String SECTIONS = "sections";

  public static final String TYPE = CorePackage.PREFIX + "Book";

  public static final String COVER = "cover";

  public static final String SOURCE = "source";
  @OneToOne
  private MediaBean cover;

  @OneToOne
  private MediaBean source;

  @Path("cover")
  public MediaBean getCover() {
    return cover;
  }

  @Override
  public String getResourceType() {
    return TYPE;
  }

  @Path("source")
  public MediaBean getSource() {
    return source;
  }

  @Override
  public void readMedia(MultivaluedMap<String, MediaBean> params) {
    super.readMedia(params);
    MediaBean cover = params.getFirst(COVER);
    if (cover != null) {
      this.cover = cover;
      cover.setParent(this);
    }
    MediaBean source = params.getFirst(SOURCE);
    if (source != null) {
      this.source = source;
      source.setParent(this);
    }
  }

  public void setCover(MediaBean cover) {
    if (cover != null) {
      cover.setParent(this);
    }
    this.cover = cover;
  }

  public void setSource(MediaBean source) {
    if (source != null) {
      source.setParent(this);
    }
    this.source = source;
  }

}
