package org.cloudlet.web.core.service;

import org.cloudlet.web.core.Entry;
import org.cloudlet.web.core.Feed;

import java.util.List;

public interface FeedService<F extends Feed<E>, E extends Entry> extends Service<F> {

  List<E> findChildren(F parent, int start, int limit, Class<E> entryType);

}
