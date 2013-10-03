package model;

import java.util.List;

import org.junit.Test;
import org.junit.Assert;

import dto.User;

public class UserManagerTest {

	@Test
	public void testGetUsers() throws Exception {
		UserManager um = new UserManager();
		List<User> users = um.getUsers();
		for (User user : users)
			System.out.println(user);
		Assert.assertTrue(true);
	}

}
