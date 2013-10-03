package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dto.Buyer;
import dto.Course;
import dto.Line;
import dto.Product;
import dto.Seller;


public class Access {
	
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	public ArrayList<Course> getCourses(Connection con) throws SQLException {
		
		createTablesIfNotExist(con);
		ArrayList<Course> courseList = new ArrayList<Course>();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM courses");
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Course courseObj = new Course();
				courseObj.setId(rs.getInt("id"));
				courseObj.setName(rs.getString("name"));
				courseObj.setDuration(rs.getString("duration"));
				courseObj.setFee(rs.getDouble("fee"));
				courseList.add(courseObj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return courseList;
	}
	
	
	public ArrayList<Line> getLines(Connection con) throws SQLException {
		
		createTables_Line_IfNotExist(con);
		ArrayList<Line> lineList = new ArrayList<Line>();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM Line");
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Line LineObj = new Line();
				LineObj.setId(rs.getInt("id"));
				LineObj.setLat(rs.getInt("lat"));
				LineObj.setLng(rs.getInt("lng"));
				LineObj.setType(rs.getString("type"));
				LineObj.setVote(rs.getInt("vote"));
				LineObj.setCount(rs.getInt("count"));
				lineList.add(LineObj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lineList;
	}

	
	public int addLine(Connection con, Line line) throws SQLException {
		int lineId = 0;
		
				
		createTables_Line_IfNotExist(con);
		String Insert_Line = "INSERT INTO line ( lat, lng,  type, vote, count, id  )  VALUES (?,?,?,?,?,?)";
		
		lineId = getLineMaxId(con);
		PreparedStatement stmt = con.prepareStatement(Insert_Line); 
		
		stmt.setDouble(1, line.getLat());
		stmt.setDouble(2, line.getLng());
		stmt.setString(3, line.getType());
		stmt.setInt(4, line.getVote());
		stmt.setInt(5, line.getCount());
		stmt.setInt(6, lineId);
		
		
		stmt.execute();
		
		return lineId;
	}
	
	public int getLineMaxId(Connection con) throws SQLException {
		int lineId = 0;
		
				
		createTables_Line_IfNotExist(con);
		
		PreparedStatement stmt = con.prepareStatement("select max(id)+1 id from line");
		ResultSet rs = stmt.executeQuery();
		
		try {
			while (rs.next()) {				 
				lineId = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  
		return lineId;
	}
	
	
	public void createTables_Line_IfNotExist(Connection cnn) {
	    if (tableExists("line", cnn)) {
	         logger.info("table line already exists");
	     } else 
	     {
	         logger.info("create table courses");
	         String sql = "CREATE TABLE IF NOT EXISTS line ( id int(11) not null AUTO_INCREMENT, lat DECIMAL(10, 8), lng DECIMAL(10, 8), type varchar(100),  vote int(11), count int(11), datecreated timestamp default now(), PRIMARY KEY (`id`)    ) ";
	                 
	         executeStatement(sql, cnn); 
	         logger.info("Create data");  
	         
	     }
	         
	 }
	
	private void createTables_crowds_IfNotExist(Connection cnn) {
	    if (tableExists("crowds", cnn)) {
	         logger.info("table line already exists");
	     } else 
	     {
	         logger.info("create table courses");
	         String sql = "CREATE TABLE IF NOT EXISTS crowds ( id int(11), not null AUTO_INCREMENT, lat DECIMAL(10, 8), lng DECIMAL(10, 8), name varchar(100), datecreated timestamp default now(), PRIMARY KEY (`id`) ' ) ";
	                 
	         executeStatement(sql, cnn); 
	         logger.info("Create data");  
	         
	     }
	         
	 }
	

	 private void createTablesIfNotExist(Connection cnn) {
	       if (tableExists("courses", cnn)) {
	            logger.info("table courses already exists");
	        } else 
	        {
	            logger.info("create table courses");
	            String sql =
	                    "CREATE TABLE IF NOT EXISTS `courses`(\n" +
	                            "  `id` int(11)  NOT NULL AUTO_INCREMENT, \n" +
	                            "  `name` varchar(50) NOT NULL,           \n" +
	                            "  `duration` varchar(20) NOT NULL,       \n" +
	                            "  `fee` double NOT NULL,                 \n" +
	                            "  PRIMARY KEY (`id`)                     \n" +
                            ")                                            \n";
	            executeStatement(sql, cnn);
	            
	            logger.info("Create data");
	            
	            PreparedStatement stmt = null;
	            try {
	                stmt = cnn.prepareStatement("insert into `courses` (`id`, `name`, `duration`, `fee`)  VALUES (?,?,?,?)");
	                stmt.setInt(1, 1);
	                stmt.setString(2, "OCPJ");
	                stmt.setString(3, "4 months");
	                stmt.setDouble(4, 200);
	                stmt.execute();
	                //stmt = cnn.prepareStatement("insert into `courses` (`id`, `name`, `duration`, `fee`)  VALUES (?,?,?,?)");
	                stmt.setInt(1, 2);
	                stmt.setString(2, "web designing");
	                stmt.setString(3, "6 months");
	                stmt.setDouble(4, 180);
	                stmt.execute();
	                //stmt = cnn.prepareStatement("insert into `courses` (`id`, `name`, `duration`, `fee`)  VALUES (?,?,?,?)");
	                stmt.setInt(1, 3);
	                stmt.setString(2, "CCNA");
	                stmt.setString(3, "2 months");
	                stmt.setDouble(4, 250);
	                stmt.execute();
	            }
	            catch (Exception e) {
		            logger.log(Level.WARNING, "Exception inserting data:" + e.getMessage());
	            }
	            finally {
	                closeQuietly(stmt);
	            }
	        }
	            
	    }
	 
	 public void addProduct(int userId, String product, Connection con) throws SQLException {
		 createSellingTableIfNotExist(con);
		 executeStatement("insert into sellers values('"+userId+"','"+product+"')", con);
	 }
	 
	public List<Buyer> getBuyers(int userId, Connection con) throws SQLException {
		createSellingTableIfNotExist(con);
		createBuyersTableIfNotExist(con);
		ArrayList<Buyer> buyerList = new ArrayList<Buyer>();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM buyers WHERE product in (select product from sellers where userId='"+userId+"')");
		ResultSet rs = stmt.executeQuery();
		Buyer currBuyer = null;
		try {
			while (rs.next()) {
				if (currBuyer==null || currBuyer.getUser().getId()!=rs.getInt("userId")) {
					currBuyer = new Buyer();
					currBuyer.setUser(new UsersAccess().getUser(rs.getInt("userId"),con));
					buyerList.add(currBuyer);
				}
				Product product = new Product();
				product.setName(rs.getString("product"));
				product.setQuantity(rs.getInt("quantity"));
				currBuyer.getProducts().add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return buyerList;
	}
	 
	 private void createBuyersTableIfNotExist(Connection cnn) {
			if (tableExists("buyers", cnn)) {
				logger.info("table buyers already exists");
				return;
			}
			logger.info("create table buyers");
			String sql = "CREATE TABLE IF NOT EXISTS `buyers`(\n"
					+ "  `userid` int NOT NULL, \n"
					+ "  `product` varchar(50) NOT NULL, \n"
					+ "  `quantity` int NOT NULL, \n"
                    + "  PRIMARY KEY (`userId`,`product`)\n"
					+ ")";
			executeStatement(sql, cnn);
			logger.info("Create data");
			executeStatement("insert into buyers values ('2','Beer', 1)", cnn);
	}

	 public List<Product> getSellerProducts(Connection con) throws SQLException {
		 createSellingTableIfNotExist(con);
		 
			ArrayList<Product> productList = new ArrayList<Product>();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM sellers order by product");
			ResultSet rs = stmt.executeQuery();
			Product currProduct = null;
			try {
				while (rs.next()) {
					if (currProduct==null || !currProduct.getName().equals(rs.getString("product"))) {
						currProduct = new Product();
						currProduct.setName(rs.getString("product"));
						currProduct.setQuantity(-1);
						productList.add(currProduct);
					}
					Seller seller = new Seller();
					seller.setUser(new UsersAccess().getUser(rs.getInt("userId"), con));
					currProduct.getSellers().add(seller);
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return productList;
	 }

	 public void iWant(int userId, String product, int quantity, Connection con) {
		 createBuyersTableIfNotExist(con);
		 executeStatement("insert into buyers values('"+userId+"','"+product+"',"+quantity+")", con);
	 }
	 
	 public void iGot(int userId, String product, Connection con) {
		 createBuyersTableIfNotExist(con);
		 executeStatement("delete from buyers where userId="+userId+" and product='"+product+"'", con);
	 }
	 
	private void createSellingTableIfNotExist(Connection cnn) {
		if (tableExists("sellers", cnn)) {
			logger.info("table selling already exists");
			return;
		}
		logger.info("create table sellers");
		String sql = "CREATE TABLE IF NOT EXISTS `sellers`(\n"
				+ "  `userId` int NOT NULL, \n"
				+ "  `product` varchar(50) NOT NULL, \n"
                + "  PRIMARY KEY (`userId`,`product`) \n"
				+ ")";
		executeStatement(sql, cnn);
		logger.info("Create data");
		executeStatement("insert into sellers values (1,'Beer')", cnn);
	}
	 
	 private boolean tableExists(String tableName, Connection cnn) {
	        Statement stmt = null;
	        try {
	            stmt = cnn.createStatement();
	            stmt.execute("select * from " + tableName + " where 0=1");
	            return true;
	        } catch (SQLException e) {
	            return false;
	        } finally {
	            closeQuietly(stmt);
	        }
	    }
	 
	 
	  public void executeStatement(String sql, Connection cnn) {
	        Statement stmt = null;
	        try {
	            stmt = cnn.createStatement();
	            stmt.execute(sql);
	        } catch (SQLException e) {
	        	logger.warning(e.toString());
	            throw new RuntimeException("Exception executing '" + sql + "'", e);
	        } finally {
	            closeQuietly(stmt);
	        }
	    }
	  
	  
	  private void closeQuietly(Statement stmt) {
	        if (stmt == null) {
	            return;
	        }
	        try {
	            stmt.close();
	        } catch (Exception e) {
	            logger.log(Level.WARNING, "Ignore exception closing quietly statement", e);
	        }
	    }

	    private void closeQuietly(Connection cnn) {
	        if (cnn == null) {
	            return;
	        }
	        try {
	            cnn.close();
	        } catch (Exception e) {
	            logger.log(Level.WARNING, "Ignore exception closing quietly connection", e);
	        }
	    }

}
