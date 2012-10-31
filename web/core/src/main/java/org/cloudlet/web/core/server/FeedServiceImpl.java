package org.cloudlet.web.core.server;

import org.cloudlet.web.core.Entry;
import org.cloudlet.web.core.Feed;
import org.cloudlet.web.core.service.FeedService;

import com.google.inject.persist.Transactional;

public class FeedServiceImpl<F extends Feed<E>, E extends Entry> extends
		ServiceImpl<F> implements FeedService<F, E> {

	@Override
	@Transactional
	public E create(F parent, E child) {
		return super.create(parent, child);
	}

}
