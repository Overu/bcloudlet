package org.cloudlet.web.core.server;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class QueryFeed<E extends Item> extends Collection<E> {

}
