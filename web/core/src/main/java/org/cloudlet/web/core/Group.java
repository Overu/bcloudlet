package org.cloudlet.web.core;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.service.GroupBean;

@ImplementedBy(GroupBean.class)
public interface Group extends Resource {
  String TYPE = CorePackage.PREFIX + "Group";
}
