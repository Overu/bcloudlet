package org.cloudlet.web.core;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.cloudlet.web.core.service.FeedService;

@MappedSuperclass
public class Feed<E extends Entry> extends Content {

	private long totalResults;

	@Transient
	private List<E> entries = new ArrayList<E>();

	public long getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(long totalResults) {
		this.totalResults = totalResults;
	}

	public List<E> getEntries() {
		return entries;
	}

	public void setEntries(List<E> entries) {
		this.entries = entries;
	}

	@POST
	@Consumes(MediaType.WILDCARD)
	@Produces(MediaType.WILDCARD)
	public E create(E child) {
		child.setParent(this);
		FeedService<Feed<E>, E> service = (FeedService<Feed<E>, E>) getService();
		return service.create(this, child);
	}

	@Override
	protected <T extends Content> T create(T child) {
		E entry = (E) child;
		return (T) create(entry);
	}

	@Override
	public FeedService getService() {
		return (FeedService) super.getService();
	}

}
