package webservice;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import model.AccessManager;
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
}
