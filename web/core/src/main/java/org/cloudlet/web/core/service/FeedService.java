package org.cloudlet.web.core.service;

import java.util.List;

import org.cloudlet.web.core.Entry;
import org.cloudlet.web.core.Feed;

public interface FeedService<F extends Feed<E>, E extends Entry> extends
		Service<F> {

	E create(F parent, E child);

	List<E> findChildren(F parent, int start, int limit, Class<E> entryType);

}
