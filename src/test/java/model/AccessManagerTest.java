package model;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import dto.Buyer;

public class AccessManagerTest {

	@Test
	public void testGetBuyers() throws Exception {
		List<Buyer> buyers = new AccessManager().getBuyers(1);
		for (Buyer buyer : buyers)
			System.out.println(buyer);
		Assert.assertTrue(true);
	}

}
