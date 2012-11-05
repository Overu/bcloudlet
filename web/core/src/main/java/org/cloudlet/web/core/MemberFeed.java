package org.cloudlet.web.core;

import org.cloudlet.web.core.service.MemberFeedService;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@Handler(MemberFeedService.class)
public class MemberFeed extends Feed<Member> {
  @Override
  @XmlTransient
  public Class<Member> getEntryType() {
    return Member.class;
  }
}
