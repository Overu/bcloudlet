package org.cloudlet.web.core.shared;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.cloudlet.web.core.server.ContentType;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@TypeDef(name = "content", typeClass = ContentType.class)
@MappedSuperclass
@XmlType
public class Content {

	@Id
	protected String id;

	protected String path;

	@Version
	protected Long version;

	@ManyToOne
	protected User owner;

	@Type(type = "content")
	@Columns(columns = { @Column(name = "parentType"),
			@Column(name = "parentId") })
	private Content parent;

	@XmlTransient
	public Content getParent() {
		return parent;
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

	@XmlElement
	public String getUri() {
		return getUriBuilder().toString();
	}

	public StringBuilder getUriBuilder() {
		if (parent == null) {
			return new StringBuilder();
		}
		StringBuilder builder = parent.getUriBuilder();
		builder.append("/").append(path);
		return builder;
	}

	public Long getVersion() {
		return version;
	}

	public void setParent(final Content container) {
		this.parent = container;
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

	public void setVersion(final Long version) {
		this.version = version;
	}
}
