package com.goodow.web.security.server.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.ByteSource;
import org.cloudlet.web.core.server.JpaRealm;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserService;
import org.cloudlet.web.test.WebTest;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DataImportTest extends WebTest {

	public static List<String> getName() throws IOException {
		InputStream inputStream = DataImportTest.class
				.getResourceAsStream("三国人物.txt");
		BufferedReader bf = new BufferedReader(new InputStreamReader(
				inputStream));
		List<String> list = new ArrayList<String>();
		String str = null;
		while ((str = bf.readLine()) != null) {
			list.add(str);
		}
		inputStream.close();
		bf.close();
		return list;
	}

	String userName = null;
	String pwd = "123";
	@Inject
	private Provider<User> users;
	@Inject
	private UserService userService;
	@Inject
	SecurityManager securityManager;

	@Inject
	private Provider<EntityManager> em;

	@Test
	public void testData() throws IOException {
		em.get().getTransaction().begin();
		List<String> list = DataImportTest.getName();
		User user = null;
		for (String pm : list) {
			userName = pm;
			user = userService.findUserByUsername(userName);
			if (user != null) {
				userService.remove(user);
			}
			String hashedPwd = new SimpleHash(JpaRealm.ALGORITHM_NAME,
					pwd.toCharArray(), ByteSource.Util.bytes(pwd)).toHex();
			user = users.get();
			user.setName(userName);
			user.setEmail(hashedPwd);
			user.setPhone(pwd);
			userService.save(user);
		}

		em.get().getTransaction().commit();
	}
}
