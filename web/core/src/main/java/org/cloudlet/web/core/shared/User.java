package org.cloudlet.web.core.shared;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "t_user")
public class User extends Content {

	@NotNull(message = "你必须指定用户名")
	protected String name;

	protected String email;

	protected String phone;

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public void setName(final String value) {
		this.name = value;
	}

	public User setEmail(final String value) {
		this.email = value;
		return this;
	}

	public User setPhone(final String value) {
		this.phone = value;
		return this;
	}

}
