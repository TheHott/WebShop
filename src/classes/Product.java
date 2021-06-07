package classes;

import java.util.stream.Stream;

public class Product {
	private int ID;
	private String name;
	private float costPerOne;
	private int amount;
	private String shortDesc;
	private Category category = new Category();
	private String[][] specs; // характеристики
	private int rows;
	private int columns=2;
	private String picLink="./pics/default.png";

	public Product(int iD, String name, float costPerOne, int amountInStock, String shortDesc, Category category,
			String[][] specs, int rows, int columns, String picLink) {
		super();
		ID = iD;
		this.name = name;
		this.costPerOne = costPerOne;
		this.amount = amountInStock;
		this.shortDesc = shortDesc;
		this.category = category;
		this.specs = specs;
		this.rows = rows;
		this.columns = columns;
		this.picLink = picLink;
	}

	public String getPicLink() {
		return picLink;
	}

	public void setPicLink(String picLink) {
		this.picLink = picLink;
	}

	public Product() {
		// Auto-generated constructor
	}
	
	// Конструктор для клонирования с новым количеством.
	// (используется только в servlets/AddProductToCart.java
	public Product(Product newProd, int amount) {
		this.ID = newProd.ID;
		this.name = newProd.name;
		this.costPerOne = newProd.costPerOne;
		this.amount = amount;
		this.shortDesc = newProd.shortDesc;
		this.category = newProd.category;
		this.specs = newProd.specs;
		this.rows = newProd.rows;
		this.columns = newProd.columns;
	}

	public int getSpecsLength() {
		return specs.length;
	}
	
	public void setRows(int rows) {
		this.rows=rows;
		specs = new String[rows][columns];
	}
	public int getRows() {
		return rows;
	}
	public String[][] getSpecs() {
		return specs;
	}
	public void setSpecs(String[][] specs) {
		this.specs = specs;
	}
	public void addSpecs(String nameOfSpec, String newSpec) {
		int count = Stream.of(specs).mapToInt(m -> m.length).sum();
		specs[count][0] = nameOfSpec;
		specs[count][1] = newSpec;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public void setCategoryID(int iD) {
		category.setID(iD);
	}
	public void setCategoryName(String name) {
		category.setName(name);
	}
	public float getCostPerOne() {
		return costPerOne;
	}
	public void setCostPerOne(float costPerOne) {
		this.costPerOne = costPerOne;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
}
