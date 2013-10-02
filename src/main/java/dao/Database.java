package dao;

import javax.naming.InitialContext;

import java.sql.Connection;
import javax.sql.DataSource;
import java.util.logging.Logger;


@javax.annotation.Resource(name  = "jdbc/mydb",type=javax.sql.DataSource.class)
public class Database {
	
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	private static final String datasourceName = "jdbc/mydb";
	public Connection getConnection() throws Exception {
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(datasourceName);
			Connection conn = ds.getConnection();
			logger.info("getting connection");
			
			return conn;
		
	
		} catch (Exception e) {
			throw e;
		}
	}
	
	
}