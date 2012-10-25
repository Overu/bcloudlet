package org.cloudlet.web.core.shared;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@TypeDef(name = "principal", typeClass = ContentType.class)
@MappedSuperclass
@XmlType
public class Content {

	@Id
	protected String id;

	@Version
	protected Long version;

	@ManyToOne(targetEntity = Content.class)
	protected User owner;

	@XmlTransient
	@Type(type = "principal")
	@Columns(columns = { @Column(name = "tenantType"),
			@Column(name = "tenantName") })
	private Content tenant;

	@XmlTransient
	@Type(type = "principal")
	@Columns(columns = { @Column(name = "containerType"),
			@Column(name = "containerId") })
	private Content container;

	private String path;

	public Content getContainer() {
		return container;
	}

	public String getId() {
		return id;
	}

	public User getOwner() {
		return owner;
	}

	public String getPath() {
		return path;
	}

	@XmlTransient
	public Content getTenant() {
		return tenant;
	}

	public Long getVersion() {
		return version;
	}

	public void setContainer(final Content container) {
		this.container = container;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setOwner(final User owner) {
		this.owner = owner;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public void setTenant(final Content tenant) {
		this.tenant = tenant;
	}

	public void setVersion(final Long version) {
		this.version = version;
	}

}
