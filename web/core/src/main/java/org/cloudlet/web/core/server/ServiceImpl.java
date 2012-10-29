package org.cloudlet.web.core.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.Path;

import org.apache.commons.beanutils.BeanUtils;
import org.cloudlet.web.core.shared.Entry;
import org.cloudlet.web.core.shared.Feed;
import org.cloudlet.web.core.shared.Service;
import org.cloudlet.web.core.shared.WebPlatform;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public abstract class ServiceImpl<E extends Entry, F extends Feed<E>>
		implements Service<E, F> {

	private static final Logger logger = Logger.getLogger(ServiceImpl.class
			.getName());

	private Entry container;

	private F feed;

	private String path;

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	protected abstract Class<E> getEntryType();

	protected abstract Class<F> getFeedType();

	protected Class<? extends Service<E, F>> serviceType;

	protected ServiceImpl() {
		container = WebPlatform.get().getRoot();
		Path p = getServiceType().getAnnotation(Path.class);
		path = p.value();
	}

	public Entry getContainer() {
		return container;
	}

	public void setContainer(Entry parent) {
		this.container = parent;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	protected EntityManager em() {
		return entityManagerProvider.get();
	}

	public E getEntry(final String path) {
		F feed = getFeed();
		TypedQuery<E> query = em().createQuery(
				"from " + getEntryType().getName()
						+ " f where f.parent=:parent and f.path=:path",
				getEntryType());
		query.setParameter("parent", feed);
		query.setParameter("path", path);
		E result = query.getSingleResult();
		return result;
	}

	@Override
	public E updateEntry(String path, JSONObject json) {
		E entry = getEntry(path);
		try {
			JSONObject entryJson = (JSONObject) json.get(getEntryType()
					.getSimpleName().toLowerCase());
			Iterator<String> keys = entryJson.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				Object value = entryJson.get(key);
				BeanUtils.setProperty(entry, key, value);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		em().persist(entry);
		return entry;
	}

	public List<E> find(final Entry parent) {
		String hsql = "select e from " + getEntryType().getName()
				+ " e where e.parent=:parent";
		TypedQuery<E> query = entityManagerProvider.get().createQuery(hsql,
				getEntryType());
		query.setParameter("parent", parent);
		return query.getResultList();
	}

	@Override
	public void deleteEntry(String path) {
		E entry = getEntry(path);
		deleteEntry(entry);
	}

	@Transactional
	public void deleteEntry(final E entry) {
		em().remove(entry);
	}

	@Transactional
	public E createEntry(final E entry) {
		if (entry.getId() == null) {
			entry.setId(UUID.randomUUID().toString());
		}
		if (entry.getPath() == null) {
			entry.setPath(entry.getId());
		}
		F feed = getFeed();
		entry.setParent(feed);
		em().persist(entry);
		feed.setTotalResults(feed.getTotalResults() + 1);
		em().persist(feed); // update modification time
		return entry;
	}

	@Override
	public F getFeed() {
		if (feed == null) {
			TypedQuery<F> query = em().createQuery(
					"from " + getFeedType().getName()
							+ " f where f.parent=:parent and f.path=:path",
					getFeedType());
			query.setParameter("parent", container);
			query.setParameter("path", path);
			try {
				feed = query.getSingleResult();
			} catch (NoResultException e) {
				feed = WebPlatform.get().getInjector()
						.getInstance(getFeedType());
				feed.setId(UUID.randomUUID().toString());
				feed.setParent(container);
				feed.setPath(path);
				em().persist(feed);
			}
		}
		return feed;
	}

	@Override
	public F getFeed(int start, int limit) {
		F feed = getFeed();
		if (start >= 0 && limit > 0) {
			TypedQuery<E> query2 = em().createQuery(
					"from " + getEntryType().getName()
							+ " f where f.parent=:parent", getEntryType());
			query2.setParameter("parent", feed);
			query2.setFirstResult(start);
			query2.setMaxResults(limit);
			List<E> entries = query2.getResultList();
			feed.setEntries(entries);
		}
		return feed;
	}

	@Override
	public Service<?, ?> getService(String path, String subPath) {
		for (Method m : getServiceType().getMethods()) {
			Class<?> rt = m.getReturnType();
			if (Service.class.isAssignableFrom(rt)) {
				try {
					Service<?, ?> service = (Service<?, ?>) m
							.invoke(this, path);
					return service;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		// TODO dynamic service;
		return null;
	}

	protected abstract Class<? extends Service<E, F>> getServiceType();
}
