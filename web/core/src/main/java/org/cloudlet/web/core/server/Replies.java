package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.Replies)
@XmlType(name = CorePackage.Replies)
@Entity(name = CorePackage.Replies)
@Table(name = CorePackage.Replies)
public class Replies extends Feed<Reply> {

  @Override
  public Class<Reply> getEntryType() {
    return Reply.class;
  }

  @Override
  public String getResourceType() {
    return CorePackage.Replies;
  }

  @Override
  public Class<ReplyService> getServiceType() {
    return ReplyService.class;
  }

}
