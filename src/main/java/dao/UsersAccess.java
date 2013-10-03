package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import dto.User;

public class UsersAccess {
	
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	public User getUser(int id, Connection con) throws SQLException {
		ArrayList<User> users = getUsers(id,con);
		if (users.size()>0)
			return users.get(0);
		return null;
	}
	
	public ArrayList<User> getUsers(Connection con) throws SQLException {
		return getUsers(-1,con);
	}
	
	public ArrayList<User> getUsers(int id, Connection con) throws SQLException {
		
		createTablesIfNotExist(con);
		ArrayList<User> userList = new ArrayList<User>();
		
		String sql = "SELECT * FROM users";
		if (id>0) {
			sql += " where id="+id;
		}
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				User userObj = new User();
				userObj.setId(rs.getInt("id"));
				userObj.setFirstname(rs.getString("firstname"));
				userObj.setLastname(rs.getString("lastname"));
				userObj.setPassword(rs.getString("password"));
				userObj.setUsername(rs.getString("username"));
				userObj.setAuthmethod(rs.getString("authmethod"));
				userObj.setLat(rs.getDouble("lat"));
				userObj.setLng(rs.getDouble("lng"));
				userList.add(userObj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userList;
	}
	
public int addUser(User user, Connection con) throws SQLException {
		
		createTablesIfNotExist(con);
		PreparedStatement stmt = con.prepareStatement("insert into `users` ( `firstname`, `lastname`, `password`, `username`, `authmethod`, `lat`, `lng`)  VALUES (?,?,?,?,?,?,?)");
  
        stmt.setString(1, user.getFirstname());
        stmt.setString(2, user.getLastname());
        stmt.setString(3, user.getPassword());
        stmt.setString(4, user.getUsername());
        stmt.setString(5, user.getAuthmethod());
        stmt.setDouble(6, user.getLat());
        stmt.setDouble(7, user.getLng());
        stmt.execute();
        
        stmt = con.prepareStatement("select `id`  from `users` where `username`=?");
        
        stmt.setString(1, user.getUsername());
        stmt.execute();
		ResultSet rs = stmt.executeQuery();
		int id = 0;
		try {
			while (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	} 
	
	
	 private void createTablesIfNotExist(Connection cnn) {
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
	                            "  `lat` DECIMAL(10, 8) ,         \n" +
	                            "  `lng` DECIMAL(11, 8) ,         \n" +
	                            "  `datecreated` timestamp default now() ,         \n" +	
	                            "  PRIMARY KEY (`id`)                     \n" +
                            ")                                            \n";
	            executeStatement(sql, cnn);
	            
	            logger.info("Create user data");
	            
	            PreparedStatement stmt = null;
	            try {
	                stmt = cnn.prepareStatement("insert into `users` (`id`, `firstname`, `lastname`, `password`, `username`, `authmethod`, `lat`, `lng`)  VALUES (?,?,?,?,?,?,?,?)");
	                stmt.setInt(1, 1);
	                stmt.setString(2, "Kelley");
	                stmt.setString(3, "Mantione");
	                stmt.setString(4, "kelleypass");
	                stmt.setString(5, "kelleymantione");
	                stmt.setString(6, "twitter");
	                stmt.setDouble(7, 40.71727401);
	                stmt.setDouble(8, -74.00898606);
	                stmt.execute();
	                stmt.setInt(1, 2);
	                stmt.setString(2, "Molly");
	                stmt.setString(3, "Lopez");
	                stmt.setString(4, "mollypass");
	                stmt.setString(5, "mollylopez");
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