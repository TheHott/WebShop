package classes;

import java.util.ArrayList;

public class Order {
	private int ID;
	private String buyersFIO;
	private ArrayList<Product> product = new ArrayList<>();
	private boolean deliveryState;
	private boolean paymentState;
	private int amountOfProducts;
	private String dateOfOrder;
	private String email;
	private String phoneNumber;
	private String deliveryAddress;
	private float totalCost;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getAmountOfProducts() {
		return amountOfProducts;
	}
	public void setAmountOfProducts(int amountOfProducts) {
		this.amountOfProducts = amountOfProducts;
	}
	public String getBuyersFIO() {
		return buyersFIO;
	}
	public void setBuyersFIO(String buyersFIO) {
		this.buyersFIO = buyersFIO;
	}
	public ArrayList<Product> getProducts() {
		return product;
	}
	public void setProduct(ArrayList<Product> product) {
		this.product = product;
	}
	public void addProduct(Product product) {
		this.product.add(product);
	}
	public void removeProduct(Product product) {
		this.product.remove(product);
	}
	public boolean isDeliveryState() {
		return deliveryState;
	}
	public void setDeliveryState(boolean deliveryState) {
		this.deliveryState = deliveryState;
	}
	public boolean isPaymentState() {
		return paymentState;
	}
	public void setPaymentState(boolean paymentState) {
		this.paymentState = paymentState;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public float getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}
	public String getDateOfOrder() {
		return dateOfOrder;
	}
	public void setDateOfOrder(String dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}
	
}
