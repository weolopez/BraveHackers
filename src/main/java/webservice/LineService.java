package webservice;

import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.AccessManager;
import dto.Line;

@Path("/lineService")
public class LineService {
	
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
