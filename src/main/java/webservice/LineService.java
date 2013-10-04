package webservice;

import helper.CrowdHelper;

import java.util.ArrayList;
import java.lang.Integer;
  



import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import json.JsonLine;
import model.AccessManager;
import dto.Acknowledgement;
import dto.Line;


@Path("/lineService")
public class LineService {
	
	private CrowdHelper helper = new CrowdHelper();
	
	@GET
	@Path("/getlines")
	@Produces("application/json")
	public ArrayList<Line> getlines() {
		System.out.print("calling getlines");
		ArrayList<Line> lineList = new ArrayList<Line>();
		try {
			lineList = new AccessManager().getLines();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Line line:lineList)
		{
			System.out.print(line.toString());
		}
		return lineList;
	}
	
	
	@PUT	
	@Path("/addline")
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
			System.out.print("type---------" +jsonLine.getType());
			System.out.print("count---------" +jsonLine.getCount());
			System.out.print("vote---------" +jsonLine.getVote());
			line.setType(jsonLine.getType());
			line.setCount(helper.getInt("count",jsonLine.getCount(),false,0));
			line.setLat(helper.getDouble("lat",jsonLine.getLat(),false,0));
			line.setLng(helper.getDouble("lng",jsonLine.getLng(),false,0));
			System.out.print("line added to DB: " +line.toString());
			lineId = new AccessManager().addLine(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("lineId: " +lineId);			
		return Integer.toString(lineId);
		 
	}
	
	
	// $http.get("/BraveHackers/crowds/lineService/updateLineLocation",{params: {lineId: "1", lat:"56.656576565",lng:"23.43244242"}});

	@GET
	@Produces("application/json")
	@Path("/updateLineLocation")
	public Acknowledgement updateLineLocation(@QueryParam("lineId" ) String lineId , @QueryParam("lat" ) String inlat , 
			@QueryParam("lng" ) String inlng 
		) {
		System.out.print("lineId---------" +lineId);
		System.out.print("inlat---------" +inlat);
		System.out.print("inlng---------" +inlng);
	
		Acknowledgement ack = new Acknowledgement();
		ack.setSuccess(false);		
		try
		{
			double lat = helper.getDouble("lat",inlat,false,0);
			double lng = helper.getDouble("lng",inlng,false,0);
			int lineIdInt = helper.getInt("lineId",lineId,false,0);
			new AccessManager().updateLineLocation(lineIdInt,lat,lng);
			ack.setSuccess(true);
		} catch (Exception e) {
			ack.setReason(e.getMessage());
		}
		return ack;
		
	}

	// $http.get("/BraveHackers/crowds/lineService/addSmiley",{params: {lineId: "1"}});
	//$http.get("/BraveHackers/crowds/lineService/addSmiley",{params: {lineId: "1", count:"78"}});
	@GET
	@Produces(MediaType.TEXT_PLAIN)	
	@Path("/addSmiley")
	public String addSmiley(@QueryParam("lineId" ) String lineId , @QueryParam("count" ) String count
		) {
		int numSmileys = 0;
		System.out.print("lineId---------" +lineId);		
		try
		{
			int lineIdInt = helper.getInt("lineId",lineId,false,0);
			numSmileys = new AccessManager().addSmiley(lineIdInt);
			if (count!=null & count.trim().length() !=0)
			{
				//count passed, update it
				int countIdInt = helper.getInt("count",count,false,0);
				System.out.print("updating count to ---------" +countIdInt);
				new AccessManager().setNewLineCount(lineIdInt, countIdInt);
			}
			System.out.print("numSmileys---------" +numSmileys);
		} catch (Exception e) {
		}
		return Integer.toString(numSmileys);
		
	}
	
	// $http.get("/BraveHackers/crowds/lineService/setNewLineCount",{params: {lineId: "1", count: "40"}});

	@GET
	@Produces("application/json")
	@Path("/setNewLineCount")
	public Acknowledgement setNewLineCount(@QueryParam("lineId" ) String lineId, @QueryParam("count" ) String count 
		) {
		
		int numSmileys = 0;
		System.out.print("lineId---------" +lineId);
		System.out.print("count---------" +count);
		Acknowledgement ack = new Acknowledgement();
		ack.setSuccess(false);		
		try
		{
			int lineIdInt = helper.getInt("lineId",lineId,false,0);
			int countIdInt = helper.getInt("count",count,false,0);
			new AccessManager().setNewLineCount(lineIdInt, countIdInt);
			ack.setSuccess(true);
		} catch (Exception e) {
			ack.setReason(e.getMessage());
		}
		return ack;
		
	}


	
	
	
	
	
	
	
}
