package org.cloudlet.web.core.bean;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlType;

@XmlType
@Entity
@Path("media")
public class MediaBean extends ResourceBean {

}
