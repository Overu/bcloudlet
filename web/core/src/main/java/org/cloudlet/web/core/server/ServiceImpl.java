package org.cloudlet.web.core.server;

import java.util.UUID;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.cloudlet.web.core.Content;
import org.cloudlet.web.core.service.Service;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class ServiceImpl<T extends Content> implements Service<T> {

	private static final Logger logger = Logger.getLogger(ServiceImpl.class
			.getName());
	@Inject
	private Provider<EntityManager> entityManagerProvider;

	protected EntityManager em() {
		return entityManagerProvider.get();
	}

	@Transactional
	protected <CHILD extends Content> CHILD create(T parent, CHILD child) {
		child.setParent(parent);
		if (child.getId() == null) {
			child.setId(UUID.randomUUID().toString());
		}
		if (child.getPath() == null) {
			child.setPath(child.getId());
		}
		em().persist(child);
		return child;
	}

	@Override
	public <CHILD extends Content> CHILD getChild(T parent, String path,
			Class<CHILD> childType) {
		TypedQuery<CHILD> query = em().createQuery(
				"from " + childType.getName()
						+ " f where f.parent=:parent and f.path=:path",
				childType);
		query.setParameter("parent", parent);
		query.setParameter("path", path);
		CHILD result = null;
		try {
			result = query.getSingleResult();
		} catch (NoResultException e) {
			try {
				result = childType.newInstance();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			result.setPath(path);
			create(parent, result);
		}
		return result;
	}

	@Override
	public T update(T content) {
		em().persist(content);
		return content;
	}

	@Override
	public void delete(T content) {
		em().remove(content);
	}

	@Override
	public T save(T content) {
		if (content.getId() == null) {
			content.setId(UUID.randomUUID().toString());
		}
		em().persist(content);
		return content;
	}

}
