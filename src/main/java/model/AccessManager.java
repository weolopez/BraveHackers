package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Access;
import dao.Database;
import dto.Buyer;
import dto.Course;
import dto.Line;



public class AccessManager {
	public ArrayList<Course> getCourses() throws Exception {
		ArrayList<Course> courseList = new ArrayList<Course>();
		Access access = new Access();
		courseList = access.getCourses(getConnection());
		return courseList;
	}
	
	
	public ArrayList<Line> getLines() throws Exception {
		ArrayList<Line> lineList = new ArrayList<Line>();
		Access access = new Access();
		lineList = access.getLines(getConnection());
		return lineList;
	}
	
	public String addLine(Line line) throws Exception {
		//ArrayList<Line> lineList = new ArrayList<Line>();
		Access access = new Access();
		String lineId = access.addLine(getConnection(), line);
		return lineId;
	}
	
	public List<Buyer> getBuyers(int userId) throws SQLException, Exception {
		return new Access().getBuyers(userId, getConnection());
	}
	
	private Connection getConnection() throws Exception {
		Database db = new Database();
		return db.getConnection();
	}
}
