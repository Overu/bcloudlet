package org.cloudlet.web.core.bean;


import org.cloudlet.web.core.service.MemberService;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Handler(MemberService.class)
public class GroupMember extends MemberBean {

  @ManyToOne
  private GroupBean group;

  public GroupBean getGroup() {
    return group;
  }

  public void setGroup(GroupBean group) {
    this.group = group;
  }

}
