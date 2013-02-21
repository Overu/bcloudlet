package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import java.security.Principal;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.Group)
@XmlType(name = CorePackage.Group)
@Entity(name = CorePackage.Group)
@Table(name = CorePackage.Group)
public class Group extends Entry implements Principal {

  protected String name;

  @OneToOne
  protected Groups groups;

  @OneToOne
  protected Members members;

  @Path("groups")
  @XmlTransient
  public Groups getGroups() {
    return groups;
  }

  @Path("members")
  @XmlTransient
  public Members getMembers() {
    return members;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getResourceType() {
    return CorePackage.Group;
  }

  @Override
  public Class<GroupService> getServiceType() {
    return GroupService.class;
  }

  public void setName(String name) {
    this.name = name;
  }
}
