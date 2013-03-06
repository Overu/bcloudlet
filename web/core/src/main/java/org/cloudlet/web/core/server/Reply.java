package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.Reply)
@XmlType(name = CorePackage.Reply)
@Entity(name = CorePackage.Reply)
@Table(name = CorePackage.Reply)
public class Reply extends Entry {

  protected String deviceType;

  public String getDeviceType() {
    return deviceType;
  }

  @Override
  public String getResourceType() {
    return CorePackage.Reply;
  }

  @Override
  public Class<ReplyService> getServiceType() {
    return ReplyService.class;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

}
