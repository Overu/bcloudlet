package org.cloudlet.web.core.service;

import org.cloudlet.web.core.Entry;
import org.cloudlet.web.core.Feed;

public interface FeedService<F extends Feed<E>, E extends Entry> extends
		Service<F> {

	E create(F parent, E child);

}
