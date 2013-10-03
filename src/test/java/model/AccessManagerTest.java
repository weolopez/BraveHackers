package model;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.Access;
import dao.Database;
import dto.Buyer;
import dto.Product;

public class AccessManagerTest {

	@BeforeClass
	public static void setUp() {
		try {new Access().executeStatement("drop table sellers", new Database().getConnection());} catch (Exception e) {}
		try {new Access().executeStatement("drop table buyers", new Database().getConnection());} catch (Exception e) {}
	}
	
	@Test
	public void testGetBuyers() throws Exception {
		List<Buyer> buyers = new AccessManager().getBuyers(1);
		for (Buyer buyer : buyers)
			System.out.println(buyer);
		Assert.assertTrue(true);
	}

	@Test
	public void testAddProduct() throws SQLException, Exception {
		new AccessManager().addProduct(1, "Wine");
		Assert.assertTrue(true);
		try {
			new AccessManager().addProduct(1, "Wine");
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
		new AccessManager().addProduct(1, "Cigars");
		Assert.assertTrue(true);
	}
	
	@Test
	public void testGetSellerProducts() throws SQLException, Exception {
		List<Product> products = new AccessManager().getSellerProducts();
		for (Product product : products)
			System.out.println(product);
		Assert.assertTrue(true);
	}
	
	@Test
	public void testIWant() throws Exception {
		new AccessManager().iWant(1, "Burger", 1);
		Assert.assertTrue(true);
		try {
			new AccessManager().iWant(1, "Burger", 1);
			Assert.assertTrue(false);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
		new AccessManager().iWant(1, "Fries", 1);
		Assert.assertTrue(true);
	}
	
	public void testIGot() throws Exception {
		new AccessManager().iWant(1, "Milkshake", 1);
		Assert.assertTrue(true);
		new AccessManager().iGot(1, "Milkshake");
		Assert.assertTrue(true);
		new AccessManager().iWant(1, "Milkshake", 1);
		Assert.assertTrue(true);
	}
}
