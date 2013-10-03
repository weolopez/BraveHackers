package webservice;

import java.util.ArrayList;
import java.lang.Integer;
  
import javax.ws.rs.Consumes;
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
	@Path("/addline")
	@Consumes("application/json")
	@Produces(MediaType.TEXT_PLAIN)	
	public String addline(Line line
			) {
		int lineId = 0;
		 	
		try
		{
			System.out.print("lat---------" +line.getLat());
			System.out.print("lng---------" +line.getLng());
			System.out.print("type---------" +line.getType());
			System.out.print("count---------" +line.getCount());
			System.out.print("vote---------" +line.getVote());
		
		 
		 lineId = new AccessManager().addLine(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("lineId: " +lineId);			
		return Integer.toString(lineId);
		 
	}


	
	
	
	
	
	
	
}
