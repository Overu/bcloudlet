package org.cloudlet.web.core.bean;

import org.cloudlet.web.core.service.MediaService;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlType;

@XmlType
@Entity
@Path("media")
@Handler(MediaService.class)
public class MediaBean extends ResourceBean {

}
