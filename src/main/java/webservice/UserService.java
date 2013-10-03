package webservice;

import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.UserManager;
import dto.Acknowledgement;
import dto.User;
import helper.CrowdHelper;

@Path("/userService")
public class UserService {
	
	private CrowdHelper helper = new CrowdHelper();
	@GET
	@Path("/getUsers")
	@Produces("application/json")
	public ArrayList<User> getUsers() {
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
	@Path("/addUser")
	public String addUser( @FormParam("firstName" ) String firstName , @FormParam("lastName" ) String lastName,
			@FormParam("password" ) String password ,
			@FormParam("userName" ) String userName ,
			@FormParam("authMethod" ) String authMethod ,
			@FormParam("lat" ) String inlat ,
			@FormParam("lng" ) String inlng 
		) {
		
		int id = 0;
		try
		{
			User user = new User();
			user.setFirstname(helper.getValue("firstName", firstName));
			user.setLastname(helper.getValue("lastName", lastName));
			user.setUsername(helper.getValue("userName", userName));
			user.setPassword(helper.getValue("password", password));
			user.setAuthmethod(helper.getValue("authMethod", authMethod));
			double lat = helper.getDouble("lat",inlat,false,0);
			user.setLat(lat);
			double lng = helper.getDouble("lng",inlng,false,0);	
			user.setLng(lng);
			id = new UserManager().addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(id);
		
	}
	

	
	@PUT
	@Produces("application/json")
	@Path("/updateUserLocation")
	public Acknowledgement updateUserLocation( @FormParam("lat" ) String inlat , @FormParam("lng" ) String inlng,
			@FormParam("userId" ) String userId 
		) {
		Acknowledgement ack = new Acknowledgement();
		ack.setSuccess(false);		
		try
		{
			double lat = helper.getDouble("lat",inlat,false,0);
			double lng = helper.getDouble("lng",inlng,false,0);
			int userIdInt = helper.getInt("userId",userId,false,0);
			new UserManager().updateLocation(lat,lng,userIdInt);
			ack.setSuccess(true);
		} catch (Exception e) {
			ack.setReason(e.getMessage());
		}
		return ack;
		
	}
	
	@PUT
	@Produces("application/json")
	@Path("/addLineToUser")
	public Acknowledgement addLineToUser( @FormParam("lineId" ) String lineId , @FormParam("userId" ) String userId
		) { 
		Acknowledgement ack = new Acknowledgement();
		ack.setSuccess(false);
		
		try {
			int lineIdInt = helper.getInt("lineId",lineId,false,0);
			int userIdInt = helper.getInt("userId",userId,false,0);
			new UserManager().updateLine(lineIdInt, userIdInt);
			ack.setSuccess(true);
		} catch (Exception e) {
			ack.setReason(e.getMessage());
		}
		return ack;
		
	}
	
	
	
	
}
