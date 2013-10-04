package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import dto.LostThing;

public class LostThingsAccess extends Access{
	
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	public LostThing getLostThing(int id, Connection con) throws SQLException {
		ArrayList<LostThing> things = getLostThings(id,con);
		if (things.size()>0)
			return things.get(0);
		return null;
	}
	
	public ArrayList<LostThing> getLostThings(Connection con) throws SQLException {
		return getLostThings(-1,con);
	}
	
	public ArrayList<LostThing> getLostThings(int id, Connection con) throws SQLException {
		
		createUserTablesIfNotExist(con);
		ArrayList<LostThing> thingList = new ArrayList<LostThing>();
		
		String sql = "SELECT * FROM lostthings";
		if (id>0) {
			sql += " where id="+id;
		}
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				LostThing thing = new LostThing();
				thing.setId(rs.getInt("id"));
				thing.setUserid(rs.getInt("userid"));
				thing.setThing(rs.getString("thing"));
				thing.setFound(rs.getInt("found"));			
				thingList.add(thing);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return thingList;
	}
	
	public int addLostThing(LostThing lostThing, Connection con) throws SQLException {
		
		createUserTablesIfNotExist(con);
		PreparedStatement stmt = con.prepareStatement("insert into lostthings ( id, userid, thing, found)  VALUES (?,?,?,?)");
  
		int id = getLostThingsMaxId(con);
        stmt.setInt(1, id);
        stmt.setInt(2, lostThing.getUserid());
        stmt.setString(3,lostThing.getThing());
        stmt.setInt(4, lostThing.getFound());
       
        stmt.execute();
		return id;
	} 
	
    public int updateFound(int id, Connection con) {
	    int userid = 0;
    	createLostThingsTablesIfNotExist(con);
    	try {
    		PreparedStatement stmt = con.prepareStatement("update lostthings set found=? where id=?");
    		stmt.setInt(1, 1);
    		stmt.setInt(2, id);
    		stmt.execute();
    		LostThing lostThing = getLostThing(id, con);
    		userid = lostThing.getUserid();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return userid;
    } 
    
  
	 private void createLostThingsTablesIfNotExist(Connection cnn) {
		 	     
		   createUserTablesIfNotExist(cnn);
	           
	       if (tableExists("lostthings", cnn)) {
	            logger.info("table lostthings already exists");
	       } 
	       else 
	       {
	            logger.info("create table lostthings");
	            String sql =
	                    "CREATE TABLE IF NOT EXISTS lostthings(\n" +
	                            "  id int(11)  NOT NULL AUTO_INCREMENT, \n" +
	                            "  userid int(11) , \n" +
	                            "  thing varchar(50) NOT NULL,           \n" +
	                            "  found int(11) , \n" +
	                            "  PRIMARY KEY (id),                    \n" +
	                            "  FOREIGN KEY (userid) REFERENCES users(id)                     \n" +
                            ")                                            \n";
	            
	            
	            executeStatement(sql, cnn);
	          
	        }
	            
	    }
	 
	 public int getLostThingsMaxId(Connection con) throws SQLException {
			int lostId = 0;				
			createLostThingsTablesIfNotExist(con);
			
			PreparedStatement stmt = con.prepareStatement("select max(id)+1 id from lostthings");
			ResultSet rs = stmt.executeQuery();
			
			try {
				while (rs.next()) {				 
					lostId = rs.getInt("id");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}  
			return lostId;
		}
	 
	 
}
