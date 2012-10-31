package org.cloudlet.web.core.service;

import org.cloudlet.web.core.Content;

public interface Service<T extends Content> {

	<CHILD extends Content> CHILD getChild(T parent, String path,
			Class<CHILD> childType);

	T update(T content);

	T save(T content);

	void delete(T content);

}
