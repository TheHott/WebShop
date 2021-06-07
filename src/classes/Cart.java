package classes;

import java.util.ArrayList;

public class Cart {
	private int id;
	private String ownersLogin = "guest";
	private float totalCost = 0;
	private ArrayList<Product> productList = new ArrayList<Product>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOwnersLogin() {
		return ownersLogin;
	}
	public void setOwnersLogin(String ownersLogin) {
		this.ownersLogin = ownersLogin;
	}
	public float getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}
	public ArrayList<Product> getProducts() {
		return productList;
	}
	public void setProducts(ArrayList<Product> product) {
		productList = product;
	}
	public boolean containsProduct(Product product) {
		boolean bool=false;
		for(int i=0; i<productList.size(); i++) {
			if(productList.get(i).getName().equals(product.getName()))
				bool = true;
		}
		return bool;
	}
	public Product getProduct(String productName) {
		Product product = new Product();
		for(int i=0; i<productList.size(); i++) {
			if(productList.get(i).getName().equals(productName))
				product = productList.get(i);
		}
		return product;
	}
	public void setProductsAmount(String productName, int amount) {
		for(int i=0; i<productList.size(); i++) {
			if(productList.get(i).getName().equals(productName))
				productList.get(i).setAmount(amount);
		}
	}
	public void addProduct(Product product, int amount) {
		Product newProduct = new Product(product, amount);
		productList.add(newProduct);
	}
	public void removeProduct(Product product) {
		this.productList.remove(product);
	}
	public void emptyTheProductList() {
		productList.clear();
	}
}
