package org.cloudlet.web.core.shared;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class WebPlatform {

	@Inject
	private static WebPlatform instance;

	@Inject
	private Injector injector;

	public Injector getInjector() {
		return injector;
	}

	public static WebPlatform get() {
		return instance;
	}

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	public static final String ROOT_ID = "00000000-0000-0000-0000-000000000000";

	private Entry root;

	public Entry getRoot() {
		if (root == null) {
			EntityManager em = entityManagerProvider.get();
			root = em.find(Group.class, ROOT_ID);
			if (root == null) {
				root = new Group();
				root.setId(ROOT_ID);
				em.persist(root);
			}
		}
		return root;
	}
}
