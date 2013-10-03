package webservice;

import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import model.AccessManager;
import model.UserManager;
import dto.Line;
import dto.User;

@Path("/lineService")
public class LineService {
	@GET
	@Path("/users")
	@Produces("application/json")
	public ArrayList<User> users() {
		String users = null;
		ArrayList<User> userList = new ArrayList<User>();
		try {
			userList = new UserManager().getUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
	
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/adduser")
	public String adduser( @FormParam("firstname" ) String firstname , @FormParam("lastname" ) String lastname,
			@FormParam("password" ) String password ,
			@FormParam("username" ) String username ,
			@FormParam("authmethod" ) String authmethod ,
			@FormParam("lat" ) String lat ,
			@FormParam("lng" ) String lng 
		) {
		System.out.print("---------" +firstname);
		System.out.print("---------" +lastname);
		
		User user = new User();
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setPassword(password);
		user.setUsername(username);
		user.setAuthmethod(authmethod);
		if (lat!=null && lat.trim().length() !=0)
		{
			user.setLat(Double.parseDouble(lat));
		}
		if (lng!=null && lng.trim().length() !=0)
		{
			user.setLng(Double.parseDouble(lng));
		}
		int id =0;
		try {
			id = new UserManager().addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(id);
		
	}
	
	@GET
	@Path("/getlines")
	@Produces("application/json")
	public ArrayList<Line> getlines() {
		String lines = null;
		ArrayList<Line> lineList = new ArrayList<Line>();
		try {
			lineList = new AccessManager().getLines();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lineList;
	}
	
	@PUT		
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/addline")
	public String addline(@FormParam("lat") int lat, 
			@FormParam("lng") int lng,
			@FormParam("type") String type	,	
			@FormParam("count") int count,
			@FormParam("vote") int vote
			) {
		String lineId = "0";
		
		System.out.print("---------" +lng);
		System.out.print("---------" +lat);
		System.out.print("---------" +type);
		
		 
		 Line line = new Line();
		 line.setLat(lat);
		 line.setLng(lng);
		 line.setType(type);
		 line.setCount(count);
		 line.setVote(vote);
		 
	
		 
		 
		try {
			lineId = new AccessManager().addLine(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lineId;
		 
	}
	
	
}
