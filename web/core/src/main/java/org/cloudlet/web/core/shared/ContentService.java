package org.cloudlet.web.core.shared;

import java.util.List;

import com.google.inject.persist.Transactional;

public interface ContentService<T extends Content> {

	Content getContainer();

	void setContainer(Content container);

	T getById(final String id);

	List<T> find(final Content container);

	@Transactional
	void remove(final T domain);

	T save(final T entity);

}
