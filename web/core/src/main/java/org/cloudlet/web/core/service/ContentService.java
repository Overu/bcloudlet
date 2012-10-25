package org.cloudlet.web.core.service;

import javax.ws.rs.PathParam;

import org.cloudlet.web.core.shared.Content;

public interface ContentService<T extends Content> {

	public T getById(@PathParam("id") String id);

}
