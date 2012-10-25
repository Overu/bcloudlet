package org.cloudlet.web.core.shared;

public enum GroupPermission implements Permission {

	READ,

	// add child group
	ADD_CHILD,

	// 修改、删除空间
	ADMIN;
}
