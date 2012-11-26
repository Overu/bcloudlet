package org.cloudlet.web.core.bean;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity
public class MemberFeedBean extends FeedBean<MemberBean> {

	@Override
	@XmlTransient
	public Class<MemberBean> getEntryType() {
		return MemberBean.class;
	}

}
