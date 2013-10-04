package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		PreparedStatement stmt = con.prepareStatement("insert into users ( firstname, lastname, password, username, authmethod, lat, lng, id)  VALUES (?,?,?,?,?,?,?,?)");
  
		int id = getUserMaxId(con);
        stmt.setString(1, user.getFirstname());
        stmt.setString(2, user.getLastname());
        stmt.setString(3, user.getPassword());
        stmt.setString(4, user.getUsername());
        stmt.setString(5, user.getAuthmethod());
        stmt.setDouble(6, user.getLat());
        stmt.setDouble(7, user.getLng());
        stmt.setInt(8,id);
        stmt.execute();
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

	
	 public int getUserMaxId(Connection con) throws SQLException {
			int userId = 0;				
			createUserTablesIfNotExist(con);
			
			PreparedStatement stmt = con.prepareStatement("select max(id)+1 id from users");
			ResultSet rs = stmt.executeQuery();
			
			try {
				while (rs.next()) {				 
					userId = rs.getInt("id");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}  
			return userId;
		}
	 
	 
}
