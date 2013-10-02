package model;

import java.sql.Connection;
import java.util.ArrayList;
import dao.Access;
import dao.Database;
import dto.Course;
import dto.Line;



public class AccessManager {
	public ArrayList<Course> getCourses() throws Exception {
		ArrayList<Course> courseList = new ArrayList<Course>();
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		courseList = access.getCourses(con);
		return courseList;
	}
	
	
	public ArrayList<Line> getLines() throws Exception {
		ArrayList<Line> lineList = new ArrayList<Line>();
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		lineList = access.getLines(con);
		return lineList;
	}
	
	public String addLine(Line line) throws Exception {
		//ArrayList<Line> lineList = new ArrayList<Line>();
		Database db = new Database();
		Connection con = db.getConnection();
		Access access = new Access();
		String lineId = access.addLine(con, line);
		return lineId;
	}
	
}
