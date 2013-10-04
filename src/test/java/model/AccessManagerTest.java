package model;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import dao.Access;
import dao.Database;
import dto.Buyer;
import dto.Line;
import dto.Product;
import dto.User;

@Ignore 
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
	
	@Test
	public void testGetLine() throws Exception {
		Line line = new AccessManager().getLine(1);
		System.out.println("line: " + line.toString());
		Assert.assertTrue(true);
	}
	
	@Test
	public void testAddLine() throws Exception {
		Line line = new Line();
		line.setCount(4);
		line.setLat(56.56785858765);
		line.setLng(34.354353343244);
		int lineid = new AccessManager().addLine(line);
		System.out.println("lineid: " + lineid);
		Assert.assertTrue(true);
	}
	@Test
	public void testAddSmiley() throws Exception {
		Line line = new Line();
		line.setCount(4);
		line.setLat(56.56785858765);
		line.setLng(34.354353343244);
		int lineid = new AccessManager().addLine(line);
		int votes = new AccessManager().addSmiley(lineid);
		Assert.assertTrue(votes == 1);
	}
	
	@Test
	public void testSetNewLineCounty() throws Exception {
		Line line = new Line();
		line.setCount(4);
		line.setLat(56.56785858765);
		line.setLng(34.354353343244);
		int lineid = new AccessManager().addLine(line);
		new AccessManager().setNewLineCount(lineid, 200);
		Line oldLine = new AccessManager().getLine(lineid);
		Assert.assertTrue(oldLine.getCount() == 200);
	}
	
	@Test
	public void testUpdateLineLocation() throws Exception {
		Line line = new Line();
		line.setCount(5);
		line.setLat(56.56785858765);
		line.setLng(34.354353343244);
		int lineid = new AccessManager().addLine(line);
		new AccessManager().updateLineLocation(lineid, 45.3433353,67.758757575);
		Assert.assertTrue(true);
	}
}
