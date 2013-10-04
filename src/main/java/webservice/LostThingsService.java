package webservice;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.LostThingsManager;
import dto.LostThing;
import helper.CrowdHelper;

@Path("/lostThingsService")
public class LostThingsService {
	
	private CrowdHelper helper = new CrowdHelper();
	@GET
	@Path("/getAllLostThings")
	@Produces("application/json")
	public ArrayList<LostThing> getAllLostThings() {
		ArrayList<LostThing> lostList = new ArrayList<LostThing>();
		try {
			lostList = new LostThingsManager().getLostThings();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lostList;
	}
	
	@GET
	@Path("/getLostThing")
	@Produces("application/json")
	public LostThing getLostThing(@QueryParam("id" ) String strid) {
		LostThing lostThing = new LostThing();
		try {
			int id = helper.getInt("id", strid, false, 0);
			lostThing = new LostThingsManager().getLostThing(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lostThing;
	}
	
	//returns id of lost thing
	@PUT	
	@Path("/addLostThing")
	@Consumes("application/json")
	@Produces(MediaType.TEXT_PLAIN)	
	public String addLostThing(LostThing lostThing
			) {
		int lostthingid = 0;
		 	
		try
		{
			System.out.print("id---------" +lostThing.getId());
			System.out.print("userid---------" +lostThing.getUserid());
			System.out.print("thing---------" +lostThing.getThing());
			System.out.print("found---------" +lostThing.getFound());		 
			lostthingid = new LostThingsManager().addLostThing(lostThing);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("lostthingid: " +lostthingid);			
		return Integer.toString(lostthingid);
		 
	}
	
	//returns userid of the found thing
	@GET
	@Produces(MediaType.TEXT_PLAIN)	
	@Path("/foundSomething")
	public String foundSomething( @QueryParam("id" ) String id 
		) {
		System.out.print("id---------" +id);
		int userid = 0;
		try
		{
			int lostthingid = helper.getInt("id",id,false,0);
			userid = new LostThingsManager().updateFound(lostthingid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("userid: " +userid);			
		return Integer.toString(userid);	
		
	}
	
}
