package org.cloudlet.web.core;

import javax.persistence.EntityManager;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.cloudlet.web.core.service.Service;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
@Path("/")
public class WebPlatform {

	@Inject
	private static WebPlatform instance;

	@Inject
	private Injector injector;

	public Injector getInjector() {
		return injector;
	}

	public static WebPlatform getDefault() {
		return instance;
	}

	public <T extends Service<?>> T getSerivce(Class<T> type) {
		// TODO proxy-based creation
		return injector.getInstance(type);
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
				root.save();
			}
		}
		return root;
	}

	@Path("{path}")
	public Content getContent(@PathParam("path") String path) {
		return getRoot().getChild(path);
	}
}
