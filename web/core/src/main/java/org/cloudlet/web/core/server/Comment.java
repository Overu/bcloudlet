package org.cloudlet.web.core.server;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Comment.TYPE_NAME)
public class Comment extends Entry {
  public static final String TYPE_NAME = CoreUtil.PREFIX + "Comment";

  public static final String REPLIES = "replies";
  @OneToOne
  private Replies replies;

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

  @Path(REPLIES)
  public Replies getReplies() {
    return replies;
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

  /*
   * (non-Javadoc)
   * 
   * @see org.cloudlet.web.core.server.Entry#init()
   */
  @Override
  protected void init() {
    super.init();
    replies = new Replies();
    replies.setPath(REPLIES);
    createReference(replies);
    update();
  }

}
