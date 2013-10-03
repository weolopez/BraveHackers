package webservice;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import model.AccessManager;
import dto.Acknowledgement;
import dto.Product;

@Path("/buyerService")
public class BuyerService {
	@GET
	@Path("/sellerProducts")
	@Produces("application/json")
	public List<Product> getSellerProducts() {
		List<Product> products = new ArrayList<Product>();
		try {
			products = new AccessManager().getSellerProducts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}
	
	@PUT
	@Path("/iWant")
	@Produces("application/json")
	public Acknowledgement iWant(@FormParam("userId" ) String userId, @FormParam("product" ) String product, @FormParam("quantity" ) String quantity) {
		Acknowledgement ack = new Acknowledgement();
		ack.setSuccess(false);
		try {
			int userIdInt = getInt("userId",userId,false,0);
			int quantityInt = getInt("quantity",quantity,true,1);
			if (product==null || product.length()==0)
				throw new Exception("product cannot be blank");
			new AccessManager().iWant(userIdInt, product, quantityInt);
			ack.setSuccess(true);
		} catch (Exception e) {
			ack.setReason(e.getMessage());
		}
		return ack;
	}

	@DELETE
	@Path("/iGot")
	@Produces("application/json")
	public Acknowledgement iGot(@FormParam("userId" ) String userId, @FormParam("product" ) String product) {
		Acknowledgement ack = new Acknowledgement();
		ack.setSuccess(false);
		try {
			int userIdInt = getInt("userId",userId,false,0);
			if (product==null || product.length()==0)
				throw new Exception("product cannot be blank");
			new AccessManager().iGot(userIdInt, product);
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
