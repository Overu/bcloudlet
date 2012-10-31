package org.cloudlet.web.core.service;

import org.cloudlet.web.core.Content;
import org.cloudlet.web.core.Entry;

public interface EntryService<E extends Entry> extends Service<E> {
	<CHILD extends Content> CHILD create(E parent, CHILD child);
}
