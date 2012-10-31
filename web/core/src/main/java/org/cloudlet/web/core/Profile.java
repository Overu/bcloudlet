package org.cloudlet.web.core;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlType;

import org.cloudlet.web.core.service.ProfileService;

@XmlType
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "t_profile")
@Handler(ProfileService.class)
public class Profile extends Entry {

	protected String name;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
