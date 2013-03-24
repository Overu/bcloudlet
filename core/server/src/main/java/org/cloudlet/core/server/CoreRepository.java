package org.cloudlet.core.server;

import org.glassfish.jersey.server.mvc.Template;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = CoreRepository.TYPE_NAME)
@Template
@Path("/")
@Produces("text/html;qs=5")
public class CoreRepository extends Repository {

  public static final String TYPE_NAME = CoreUtil.PREFIX + "CoreRepository";

}
