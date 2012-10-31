package org.cloudlet.web.core.server;

import org.cloudlet.web.core.Entry;
import org.cloudlet.web.core.service.EntryService;

import com.google.inject.persist.Transactional;

public class EntryServiceImpl<E extends Entry> extends ServiceImpl<E> implements
		EntryService<E> {

	@Transactional
	public <CHILD extends org.cloudlet.web.core.Content> CHILD create(E parent,
			CHILD child) {
		return super.create(parent, child);
	}

}
