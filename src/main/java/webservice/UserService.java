package webservice;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import json.JsonLine;

import model.AccessManager;
import model.UserManager;
import dto.Acknowledgement;
import dto.Line;
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
			@FormParam("lng" ) String inlng,
			@PathParam("firstName" ) String pfirstName,
			@QueryParam("firstName" ) String qfirstName
			
		) {
		
		System.out.println("firstName "+firstName);
		System.out.println("pfirstName "+pfirstName);
		System.out.println("qfirstName "+qfirstName);
		
		
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
	@Path("/addUserj")
	@Consumes("application/json")
	@Produces(MediaType.TEXT_PLAIN)	
	public String addline(JsonLine jsonLine
			) {
		int lineId = 0;
		Line line = new Line();
		 	
		try
		{
			System.out.print("lat---------" +jsonLine.getLat());
			System.out.print("lng---------" +jsonLine.getLng());
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("lineId: " +lineId);			
		return Integer.toString(lineId);
		 
	}
	
	
	@GET
	@Produces("application/json")
	@Path("/updateUserLocation")
	public Acknowledgement updateUserLocation( @QueryParam("lat" ) String inlat , @QueryParam("lng" ) String inlng,
			@QueryParam("userId" ) String userId 
		) {
		System.out.print("inlat---------" +inlat);
		System.out.print("inlng---------" +inlng);
		System.out.print("userId---------" +userId);
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
	
	@GET
	@Produces("application/json")
	@Path("/addLineToUser")
	public Acknowledgement addLineToUser( @QueryParam("lineId" ) String lineId , @QueryParam("userId" ) String userId
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
