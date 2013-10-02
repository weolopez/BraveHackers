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
