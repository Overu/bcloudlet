package org.cloudlet.web.core.server;


import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Replies.TYPE_NAME)
public class Replies extends Feed<Reply> {

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Replies";

  @Override
  public Class<Reply> getEntryType() {
    return Reply.class;
  }

  @Override
  public String getType() {
    return Replies.TYPE_NAME;
  }

  @Override
  public Class<ReplyService> getServiceType() {
    return ReplyService.class;
  }

}
