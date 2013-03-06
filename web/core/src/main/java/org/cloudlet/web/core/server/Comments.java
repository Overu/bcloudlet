package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.Comments)
@XmlType(name = CorePackage.Comments)
@Entity(name = CorePackage.Comments)
@Table(name = CorePackage.Comments)
public class Comments extends Feed<Comment> {

  @Override
  public Class<Comment> getEntryType() {
    return Comment.class;
  }

  @Override
  public String getResourceType() {
    return CorePackage.Comments;
  }

  @Override
  public Class<CommentService> getServiceType() {
    return CommentService.class;
  }

}
