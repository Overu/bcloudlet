package org.cloudlet.web.core.server;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Section.TYPE_NAME)
public class Section extends Entry {
  @OneToOne
  private Media media;
  public static final String TYPE_NAME = CoreUtil.PREFIX + "Section";

  public Media getMedia() {
    return media;
  }

  @Override
  public String getType() {
    return TYPE_NAME;
  }

  public void setMedia(Media media) {
    this.media = media;
  }

}
