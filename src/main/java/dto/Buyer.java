package dto;

import java.util.ArrayList;
import java.util.List;

public class Buyer {
	User user;
	List<Product> products = new ArrayList<Product>();
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append(user.toString());
		str.append("\n");
		for (Product product : products)
			str.append(product.toString()+"\n");
		return str.toString();
	}
}
