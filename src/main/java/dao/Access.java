package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import dto.Course;
import dto.Line;


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

	
	public String addLine(Connection con, Line line) throws SQLException {
		String lineId = "0";
		
		System.out.print("---------" +line.getLat());
		System.out.print("---------" +line.getLng());
		System.out.print("---------" +line.getType());
		
		
		createTables_Line_IfNotExist(con);
		String Insert_Line = "INSERT INTO line ( lat, lng,  type, vote, count  )  VALUES (?,?,?,?,?)";
		
		
		PreparedStatement stmt = con.prepareStatement(Insert_Line);
		
		stmt.setDouble(1, line.getLat());
		stmt.setInt(2, line.getLng());
		stmt.setString(3, line.getType());
		stmt.setInt(4, line.getVote());
		stmt.setInt(5, line.getCount());
		 
		
		stmt.execute();
		return lineId;
	}
	
	
	private void createTables_Line_IfNotExist(Connection cnn) {
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
	         String sql = "CREATE TABLE IF NOT EXISTS crowds ( id int(11), not null AUTO_INCREMENT, lat DECIMAL(10, 8), lng DECIMAL(10, 8), name varchar(100), PRIMARY KEY (`id`) ' ) ";
	                 
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
