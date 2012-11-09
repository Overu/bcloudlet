package org.cloudlet.web.core.service;

import org.cloudlet.web.core.Entry;
import org.cloudlet.web.core.Feed;

import java.util.List;

public interface FeedService<F extends Feed<E>, E extends Entry> extends Service<F> {

  long countEntries(F feed);

  E createEntry(F feed, E entry);

  List<E> findEntries(F feed, int start, int limit);

  E findEntry(F parent, String path);

}
