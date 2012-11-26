package org.cloudlet.web.core.bean;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlType;

@XmlType
@Entity
@Path("section")
@DefaultField(key = "title", value = "用户组")
public class SectionBean extends ResourceBean {

}
