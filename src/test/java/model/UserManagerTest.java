package model;

import java.util.List;

import org.junit.Test;
import org.junit.Assert;

import dto.Line;
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
	
	@Test
	public void addLineToUser() throws Exception {
		UserManager um = new UserManager();
		AccessManager access = new AccessManager();
		Line line = new Line();
		line.setCount(5);
		line.setType("food");
		line.setVote(10);
		line.setLat(24.2345);
		line.setLng(34.5645645);
		int lineId = access.addLine(line);
	
		
		User user = new User();
		user.setFirstname("New");
		user.setLastname("Tester");
		user.setUsername("joeyt");
		user.setPassword("pass");
		user.setAuthmethod("");
		int userId = um.addUser(user);
		um.updateLine(lineId, userId);
		for (User us : um.getUsers())
			System.out.println(us);
		System.out.println("lineId: " + lineId);
		Assert.assertTrue(true);
	}


}
