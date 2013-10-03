package dto;

import java.util.ArrayList;
import java.util.List;

public class Product {
	private String name;
	private int quantity;
	private List<Seller> sellers = new ArrayList<Seller>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public List<Seller> getSellers() {
		return sellers;
	}
	public void setSellers(List<Seller> sellers) {
		this.sellers = sellers;
	}
	
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("Product [name="+name+", quantity="+quantity+" Sellers:\n");
		for (Seller seller : sellers)
			str.append(seller.toString()+"\n");
		str.append("]");
		return str.toString();
	}
}
