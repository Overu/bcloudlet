package org.cloudlet.web.core.server;

import java.util.logging.Logger;

import javax.persistence.NoResultException;
import javax.ws.rs.Path;

import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.cloudlet.web.core.shared.Group;
import org.cloudlet.web.core.shared.Service;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserService;
import org.cloudlet.web.core.shared.UserFeed;

@Path("users")
public class UserServiceImpl extends ServiceImpl<User, UserFeed> implements
		UserService {

	private static final Logger logger = Logger.getLogger(UserServiceImpl.class
			.getName());

	public User getEntry(String id) {
		return super.getEntry(id);
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

	@Override
	protected Class<User> getEntryType() {
		return User.class;
	}

	@Override
	protected Class<UserFeed> getFeedType() {
		return UserFeed.class;
	}

	@Override
	protected Class<? extends Service<User, UserFeed>> getServiceType() {
		return UserService.class;
	}
}
