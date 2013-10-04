package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Access;
import dao.Database;
import dto.Buyer;
import dto.Line;
import dto.Product;



public class AccessManager {
	
	public ArrayList<Line> getLines() throws Exception {
		ArrayList<Line> lineList = new ArrayList<Line>();
		Access access = new Access();
		lineList = access.getLines(getConnection());
		return lineList;
	}
	
	public Line getLine(int id) throws Exception {
		Line line = new Line();
		Access access = new Access();
		line = access.getLine(id,getConnection());
		return line;
	}
	
	public int addLine(Line line) throws Exception {
		//ArrayList<Line> lineList = new ArrayList<Line>();
		Access access = new Access();
		int lineId = access.addLine(getConnection(), line);
		return lineId;
	}
	
	public int addSmiley(int id) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		return access.addSmiley(id, con);
	}
	
	public void setNewLineCount(int id, int count) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		access.setNewLineCount(id, count, con);
	}
	
	public void updateLineLocation(int id, double lat, double lng) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		access.updateLineLocation(id, lat,lng, con);
	}
	
	public List<Buyer> getBuyers(int userId) throws SQLException, Exception {
		return new Access().getBuyers(userId, getConnection());
	}
	
	public void addProduct(int userId, String productName) throws SQLException, Exception {
		new Access().addProduct(userId, productName, getConnection());
	}
	
	public List<Product> getSellerProducts() throws SQLException, Exception {
		return new Access().getSellerProducts(getConnection());
	}
	
	public void iWant(int userId, String product, int quantity) throws Exception {
		new Access().iWant(userId, product, quantity, getConnection());
	}
	
	public void iGot(int userId, String product) throws Exception {
		new Access().iGot(userId, product, getConnection());
	}
	
	private Connection getConnection() throws Exception {
		Database db = new Database();
		return db.getConnection();
	}
}
