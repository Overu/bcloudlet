package org.cloudlet.web.core.server;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.cloudlet.web.core.shared.Content;
import org.cloudlet.web.core.shared.ContentService;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.google.web.bindery.autobean.vm.impl.TypeUtils;

public class ContentServiceImpl<T extends Content> implements ContentService<T> {

	private static final Logger logger = Logger
			.getLogger(ContentServiceImpl.class.getName());

	private Content container;

	@SuppressWarnings("unchecked")
	protected ContentServiceImpl() {
		Type genericSuperClass = getClass().getGenericSuperclass();
		beanClass = (Class<T>) TypeUtils.ensureBaseType(TypeUtils
				.getSingleParameterization(ContentServiceImpl.class,
						genericSuperClass));
	}

	public Content getContainer() {
		return container;
	}

	public void setContainer(Content container) {
		this.container = container;
	}

	public T getById(final String id) {
		return em.get().find(getBeanClass(), id);
	}

	public List<T> find(final Content container) {
		String hsql = "select e from " + getBeanClass().getName()
				+ " e where e.container=:container";
		TypedQuery<T> query = em.get().createQuery(hsql, getBeanClass());
		query.setParameter("container", container);
		return query.getResultList();
	}

	@Transactional
	public void remove(final T domain) {
		em.get().remove(domain);
	}

	public T save(final T entity) {
		if (entity.getId() == null) {
			entity.setId(UUID.randomUUID().toString());
		}
		em.get().persist(entity);
		return entity;
	}

	@Inject
	private transient Provider<EntityManager> em;

	private Class<T> beanClass;

	public Class<T> getBeanClass() {
		return beanClass;
	}

	protected EntityManager em() {
		return em.get();
	}

}
