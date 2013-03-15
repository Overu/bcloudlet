package org.cloudlet.web.core.server;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Comments.TYPE_NAME)
public class Comments extends Folder<Comment> {

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Comments";

  @Override
  public Class<Comment> getEntryType() {
    return Comment.class;
  }

  @Override
  public String getType() {
    return TYPE_NAME;
  }

}
