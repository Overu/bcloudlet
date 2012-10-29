package org.cloudlet.web.core.shared;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class UsersFeed extends Feed<User> {
}
