package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.Section)
@XmlType(name = CorePackage.Section)
@Entity(name = CorePackage.Section)
@Table(name = CorePackage.Section)
public class Section extends Resource {
  @OneToOne
  private Media media;

  public Media getMedia() {
    return media;
  }

  @Override
  public String getResourceType() {
    return CorePackage.Section;
  }

  public void setMedia(Media media) {
    if (media != null) {
      media.setPath("media");
      media.setParent(this);
    }
    this.media = media;
  }

}
