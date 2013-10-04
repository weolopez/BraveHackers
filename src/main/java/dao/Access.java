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
import dto.Line;
import dto.Product;
import dto.Seller;



public class Access {
	
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	public Line getLine(int id, Connection con) throws SQLException {
		ArrayList<Line> lines = getLines(id,con);
		if (lines.size()>0)
			return lines.get(0);
		return null;
	}
	
	public ArrayList<Line> getLines(Connection con) throws SQLException {
		return getLines(-1,con);
	}
	
	public ArrayList<Line> getLines(int id, Connection con) throws SQLException {
		
		createTables_Line_IfNotExist(con);
		ArrayList<Line> lineList = new ArrayList<Line>();
		
		String sql = "SELECT * FROM line";
		if (id>0) {
			sql += " where id="+id;
		}
		PreparedStatement stmt = con.prepareStatement(sql);
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
	
	 public int addSmiley(int id, Connection con) {
		    int totalVotes = 0;
		    createTables_Line_IfNotExist(con);
	    	try {
	    		PreparedStatement stmt = con.prepareStatement("update line set vote = vote + 1 where id=?");
	    		stmt.setInt(1, id);
	    		stmt.execute();
	    		Line line = getLine(id, con);
	    		totalVotes = line.getVote();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	return totalVotes;
	    } 
	 
	 public void setNewLineCount(int id, int count, Connection con) {
		    createTables_Line_IfNotExist(con);
	    	try {
	    		PreparedStatement stmt = con.prepareStatement("update line set count = ? where id=?");
	    		stmt.setInt(1, count);
	    		stmt.setInt(2, id);
	    		stmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	 
	 public void updateLineLocation(int id, double lat, double lng, Connection con) {
		    createTables_Line_IfNotExist(con);
	    	try {
	    		PreparedStatement stmt = con.prepareStatement("update line set lat=?, lng=? where id=?");
	    		stmt.setDouble(1, lat);
	    		stmt.setDouble(2, lng);
	    		stmt.setInt(3, id);
	    		stmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
	
	
	protected void createTables_Line_IfNotExist(Connection cnn) {
	    if (tableExists("line", cnn)) {
	         logger.info("table line already exists");
	     } else 
	     {
	         logger.info("create table courses");
	         String sql = "CREATE TABLE IF NOT EXISTS line ( id int(11) not null AUTO_INCREMENT, lat DECIMAL(16, 14), lng DECIMAL(17, 14), type varchar(100),  vote int(11), count int(11), datecreated timestamp default now(), PRIMARY KEY (`id`)    ) ";
	                 
	         executeStatement(sql, cnn); 
	         logger.info("Create data");  
	         
	     }
	         
	 }
	
	 protected void createUserTablesIfNotExist(Connection cnn) {
 	     
		 createTables_Line_IfNotExist(cnn);
	           
	       if (tableExists("users", cnn)) {
	            logger.info("table users already exists");
	        } else 
	        {
	            logger.info("create table users");
	            String sql =
	                    "CREATE TABLE IF NOT EXISTS `users`(\n" +
	                            "  `id` int(11)  NOT NULL AUTO_INCREMENT, \n" +
	                            "  `firstname` varchar(50) NOT NULL,          \n" +
	                            "  `lastname` varchar(50) NOT NULL,           \n" +
	                            "  `password` varchar(50) NOT NULL,           \n" +
	                            "  `username` varchar(50) NOT NULL,           \n" +
	                            "  `authmethod` varchar(50) NOT NULL,         \n" +
	                            "  `lat` DECIMAL(16, 14) ,         \n" +
	                            "  `lng` DECIMAL(17, 14) ,         \n" +
	                            "  `datecreated` timestamp ,         \n" +	
	                            "  `lineid` int(11) , \n" +
	                            "  PRIMARY KEY (`id`),                    \n" +
	                            "  FOREIGN KEY (`lineid`) REFERENCES line(id)                     \n" +
                            ")                                            \n";
	            
	         

	            
	            executeStatement(sql, cnn);
	            
	            logger.info("Create user data");
	            
	            PreparedStatement stmt = null;
	            try {
	                stmt = cnn.prepareStatement("insert into `users` (`id`, `firstname`, `lastname`, `password`, `username`, `authmethod`, `lat`, `lng`)  VALUES (?,?,?,?,?,?,?,?)");
	                stmt.setInt(1, 1);
	                stmt.setString(2, "Walter");
	                stmt.setString(3, "White");
	                stmt.setString(4, "walterpass");
	                stmt.setString(5, "walterwhite");
	                stmt.setString(6, "twitter");
	                stmt.setDouble(7, 40.71727401);
	                stmt.setDouble(8, -74.00898606);
	                stmt.execute();
	                stmt.setInt(1, 2);
	                stmt.setString(2, "Nick");
	                stmt.setString(3, "Brody");
	                stmt.setString(4, "nickpass");
	                stmt.setString(5, "nickbrody");
	                stmt.setString(6, "twitter");
	                stmt.setDouble(7, 40.71727401);
	                stmt.setDouble(8, -74.00898606);
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
	 
	protected boolean tableExists(String tableName, Connection cnn) {
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
	  
	  
	  protected void closeQuietly(Statement stmt) {
	        if (stmt == null) {
	            return;
	        }
	        try {
	            stmt.close();
	        } catch (Exception e) {
	            logger.log(Level.WARNING, "Ignore exception closing quietly statement", e);
	        }
	    }

	    protected void closeQuietly(Connection cnn) {
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
