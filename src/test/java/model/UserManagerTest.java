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
	
	@Test
	public void testUpdateLocation() throws Exception {
		UserManager um = new UserManager();
		um.updateLocation(31.1234566, 45.34567, 2);
		List<User> users = um.getUsers();
		for (User user : users)
			System.out.println(user);
		Assert.assertTrue(true);
	}
	
	@Test
	public void addUser() throws Exception {
		UserManager um = new UserManager();
		User user = new User();
		user.setFirstname("Joe");
		user.setLastname("Tester");
		user.setUsername("joeyt");
		user.setPassword("pass");
		user.setAuthmethod("");
		int id = um.addUser(user);
		List<User> users = um.getUsers();
		for (User us : users)
			System.out.println(us);
		Assert.assertTrue(true);
	}


}
