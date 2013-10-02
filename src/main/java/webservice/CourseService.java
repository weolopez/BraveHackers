package webservice;

import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import model.AccessManager;
import dto.Course;

@Path("/courseService")
public class CourseService {
	@GET
	@Path("/courses")
	@Produces("application/json")
	public ArrayList<Course> courses() {
		String courses = null;
		ArrayList<Course> courseList = new ArrayList<Course>();
		try {
			courseList = new AccessManager().getCourses();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return courseList;
	}
}
