package org.cloudlet.web.core.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.cloudlet.web.core.shared.Feed;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserService;

@Path("users")
public class UserServiceImpl extends ContentServiceImpl<User> implements
		UserService {

	private static final Logger logger = Logger.getLogger(UserServiceImpl.class
			.getName());

	@Path("{id}")
	@GET
	@Consumes(MediaType.WILDCARD)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public User getById(@PathParam("id") String id) {
		return super.getById(id);
	}

	public User findUserByUsername(final String userName) {
		User toRtn = null;
		try {
			toRtn = em()
					.createQuery(
							"select u from com.goodow.web.security.server.domain.User u where u.userName = :userName",
							User.class).setParameter("userName", userName)
					.getSingleResult();
		} catch (NoResultException e) {
		}
		return toRtn;
	}

	@RequiresAuthentication
	public void updatePassword(final String userName, final String newPwd) {
		User user = findUserByUsername(userName);
		if (user == null) {
			throw new UnknownAccountException("找不到用户名: " + userName);
		}
		String hashedPwd = new SimpleHash(JpaRealm.ALGORITHM_NAME,
				newPwd.toCharArray(), ByteSource.Util.bytes(user.getPhone()))
				.toHex();
		user.setEmail(hashedPwd);
		em().persist(user);
	}

	@GET
	@Produces({ MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON })
	public Feed<User> getFeed() {
		Feed<User> feed = new Feed<User>();
		List<User> users = new ArrayList<User>();
		User u1 = new User();
		u1.setId("jack");
		u1.setName("Jack");
		u1.setEmail("jack@gmail.com");
		u1.setPhone("+86-13911231234");
		users.add(u1);
		User u2 = new User();
		u2.setId("rose");
		u2.setName("Rose");
		u2.setEmail("rose@gmail.com");
		u2.setPhone("13812834321");
		users.add(u2);
		feed.setEntries(users);
		feed.setTotalResults(20);
		return feed;
	}
}
