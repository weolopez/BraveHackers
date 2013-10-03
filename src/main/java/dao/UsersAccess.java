package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import dto.User;

public class UsersAccess extends Access{
	
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
		
		createUserTablesIfNotExist(con);
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
		
		createUserTablesIfNotExist(con);
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
	
    public void updateLocation(double lat, double lng, int id, Connection con) {
	
    	createUserTablesIfNotExist(con);
    	try {
    		PreparedStatement stmt = con.prepareStatement("update users set lat=?,lng=? where id=?");
    		stmt.setDouble(1, lat);
    		stmt.setDouble(2, lng);
    		stmt.setInt(3, id);
    		stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    } 
    
    public void updateLine(int lineid, int id, Connection con) {
    	
    	createUserTablesIfNotExist(con);
    	try {
    		PreparedStatement stmt = con.prepareStatement("update users set lineid=? where id=?");
    		stmt.setInt(1, lineid);
    		stmt.setInt(2, id);
    		stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    } 

	
	 private void createUserTablesIfNotExist(Connection cnn) {
		 	     
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
	                            "  `lat` DECIMAL(10, 8) ,         \n" +
	                            "  `lng` DECIMAL(11, 8) ,         \n" +
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
	 
	 
}
