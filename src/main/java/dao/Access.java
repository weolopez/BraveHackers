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
	 
	 public void addProduct(String user, String product, Connection con) throws SQLException {
		 createSellingTableIfNotExist(con);
		 executeStatement("insert into selling values('"+user+"','"+product+"')", con);
	 }
	 
	public List<Buyer> getBuyers(String user, Connection con) throws SQLException {
		createSellingTableIfNotExist(con);
		createBuyersTableIfNotExist(con);
		ArrayList<Buyer> buyerList = new ArrayList<Buyer>();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM buyers WHERE product in (select product from sellers where user='"+user+"')");
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Buyer obj = new Buyer();
				obj.setUser(rs.getString("user"));
				obj.setProduct(rs.getString("product"));
				obj.setQuantity(rs.getInt("quantity"));
				buyerList.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return buyerList;
	}
	 
	 private void createBuyersTableIfNotExist(Connection cnn) {
			if (tableExists("buyers", cnn)) {
				logger.info("table buying already exists");
				return;
			}
			logger.info("create table buyers");
			String sql = "CREATE TABLE IF NOT EXISTS `buyers`(\n"
					+ "  `user` varchar(50) NOT NULL, \n"
					+ "  `product` varchar(50) NOT NULL, \n"
					+ "  `quantity` int NOT NULL\n"
					+ ")                                            \n";
			executeStatement(sql, cnn);
			logger.info("Create data");
			executeStatement("insert into buyers values ('Barney','Beer', 1)", cnn);
	}



	private void createSellingTableIfNotExist(Connection cnn) {
		if (tableExists("selling", cnn)) {
			logger.info("table selling already exists");
			return;
		}
		logger.info("create table selling");
		String sql = "CREATE TABLE IF NOT EXISTS `selling`(\n"
				+ "  `user` varchar(50) NOT NULL, \n"
				+ "  `product` varchar(50) NOT NULL\n"
				+ ")                                            \n";
		executeStatement(sql, cnn);
		logger.info("Create data");
		executeStatement("insert into selling values ('Fred','Beer')", cnn);
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
	 
	 
	  private void executeStatement(String sql, Connection cnn) {
	        Statement stmt = null;
	        try {
	            stmt = cnn.createStatement();
	            stmt.execute(sql);
	        } catch (SQLException e) {
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
