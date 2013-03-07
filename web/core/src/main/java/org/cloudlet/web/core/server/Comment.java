package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.Comment)
@XmlType(name = CorePackage.Comment)
@Entity(name = CorePackage.Comment)
@Table(name = CorePackage.Comment)
public class Comment extends Entry {

  @OneToOne
  private Replies replies;

  protected String deviceType;

  @Basic(fetch = FetchType.LAZY)
  protected String content;

  public String getContent() {
    return content;
  }

  public String getDeviceType() {
    return deviceType;
  }

  @Path("replies")
  public Replies getReplies() {
    return replies;
  }

  @Override
  public String getResourceType() {
    return CorePackage.Comment;
  }

  @Override
  public Class<CommentService> getServiceType() {
    return CommentService.class;
  }

  public void setContent(String body) {
    this.content = body;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public void setReplies(Replies replies) {
    this.replies = replies;
  }

}
