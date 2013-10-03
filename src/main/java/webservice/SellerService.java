package webservice;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import model.AccessManager;
import dto.Acknowledgement;
import dto.Buyer;

@Path("/sellerService")
public class SellerService {
	@GET
	@Path("/buyers")
	@Produces("application/json")
	public List<Buyer> getBuyers(@QueryParam("userId" ) String userId) {
		List<Buyer> buyers = new ArrayList<Buyer>();
		try {
			int userIdInt = getInt("userId",userId,false,0);
			buyers = new AccessManager().getBuyers(userIdInt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buyers;
	}
	
	@PUT
	@Path("/addProduct")
	@Produces("application/json")
	public Acknowledgement addProduct(@FormParam("userId" ) String userId, @FormParam("product" ) String product) {
		Acknowledgement ack = new Acknowledgement();
		ack.setSuccess(false);
		try {
			int userIdInt = getInt("userId",userId,false,0);
			if (product==null || product.length()==0)
				throw new Exception("product cannot be blank");
			new AccessManager().addProduct(userIdInt, product);
			ack.setSuccess(true);
		} catch (Exception e) {
			ack.setReason(e.getMessage());
		}
		return ack;
	}

	private int getInt(String field, String value, boolean allowDefault, int defValue) throws Exception {
		if (value==null || value.length()==0) {
			if (allowDefault)
				return defValue;
			throw new Exception(field+" cannot be blank");
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new Exception(field+" must be a number");
		}
		
	}
}
