package model;

import java.util.List;


import org.junit.Test;
import org.junit.Assert;

import dto.LostThing;
import dto.User;
import org.junit.Ignore;

@Ignore
public class LostThingsManagerTest {

	@Test
	public void testGetLostThings() throws Exception {
		LostThingsManager manager = new LostThingsManager();
		List<LostThing> things = manager.getLostThings();
		for (LostThing thing : things)
			System.out.println(thing.toString());
		Assert.assertTrue(true);
	}
	
	@Test
	public void testGetLostThing() throws Exception {
		LostThingsManager manager = new LostThingsManager();
		
		UserManager um = new UserManager();
		User user = new User();
		user.setFirstname("New");
		user.setLastname("Tester");
		user.setUsername("joeyt");
		user.setPassword("pass");
		user.setAuthmethod("");
		int userId = um.addUser(user);
		LostThing thing = new LostThing();
		thing.setFound(0);
		thing.setThing("phone");
		thing.setUserid(userId);
		int id = manager.addLostThing(thing);
		LostThing mything = manager.getLostThing(id);
		
		System.out.println(thing.toString());
		Assert.assertTrue(mything.getThing().equals("phone"));
	}
	
	@Test
	public void testUpdateFound() throws Exception {
		
		UserManager um = new UserManager();
		User user = new User();
		user.setFirstname("New");
		user.setLastname("Tester");
		user.setUsername("joeyt");
		user.setPassword("pass");
		user.setAuthmethod("");
		int userId = um.addUser(user);
		LostThingsManager manager = new LostThingsManager();
		LostThing thing = new LostThing();
		thing.setFound(0);
		thing.setThing("keys");
		thing.setUserid(userId);
		int id = manager.addLostThing(thing);
		manager.updateFound(id);
		
		LostThing updatedThing = manager.getLostThing(id);
		
		System.out.println(updatedThing);
		Assert.assertTrue(updatedThing.getFound()==1);
	}
	
	@Test
	public void testAddLostThing() throws Exception {
		UserManager um = new UserManager();
		User user = new User();
		user.setFirstname("New");
		user.setLastname("Tester");
		user.setUsername("joeyt");
		user.setPassword("pass");
		user.setAuthmethod("");
		int userId = um.addUser(user);
		LostThingsManager manager = new LostThingsManager();
		LostThing thing = new LostThing();
		thing.setFound(0);
		thing.setThing("wallet");
		thing.setUserid(userId);
		int id = manager.addLostThing(thing);
		LostThing mything = manager.getLostThing(id);
		Assert.assertTrue(mything.getThing().equals("wallet"));
	}
	
	

}
