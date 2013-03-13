package org.cloudlet.web.core.server;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Reply.TYPE_NAME)
public class Reply extends Entry {

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Reply";

  protected String deviceType;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  protected String content;

  public String getContent() {
    return content;
  }

  public String getDeviceType() {
    return deviceType;
  }

  @Override
  public String getType() {
    return Reply.TYPE_NAME;
  }

  public void setContent(String body) {
    this.content = body;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }
}
