package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.Section)
@XmlType(name = CorePackage.Section)
@Entity(name = CorePackage.Section)
@Table(name = CorePackage.Section)
public class Section extends Entry {
  @OneToOne
  private Media media;

  public Media getMedia() {
    return media;
  }

  @Override
  public String getResourceType() {
    return CorePackage.Section;
  }

  @Override
  @XmlTransient
  public Class<SectionService> getServiceType() {
    return SectionService.class;
  }

  public void setMedia(Media media) {
    this.media = media;
  }

}
