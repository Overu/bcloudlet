package org.cloudlet.web.core.bean;

import org.cloudlet.web.core.service.SectionService;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlType;

@XmlType
@Entity
@Table(name = "t_section")
@Handler(SectionService.class)
@Path("section")
@DefaultField(key = "title", value = "用户组")
public class SectionBean extends ResourceBean {

}
