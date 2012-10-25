package org.cloudlet.web.core.shared;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Group extends Content {

	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}