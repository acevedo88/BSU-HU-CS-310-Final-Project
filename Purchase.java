public class Purchase {
	private int itemId;
	private int quantity;
	private String purchaseDate;
	private String itemCode;
	private String itemDescription;
	private double price;
	
	
	public Purchase(String itemCode, String itemDescription, double price, int itemID, int quantity, String purchaseDate) {
		this.itemCode = itemCode;
		this.itemDescription = itemDescription;
		this.price = price;
        this.itemId = itemID;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
        
    }
	public Purchase(int itemID, int quantity, String purchaseDate) {
        this.itemId = itemID;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
        
    }
	
	public Purchase(int itemID, int quantity) {
        this.itemId = itemID;
        this.quantity = quantity;
        
    }
	public Purchase(String itemCode, int quantity) {
        this.itemCode = itemCode;
        this.quantity = quantity;
        
    }
	
    public String toString(){
        return String.format("(%s, %s, %s, %s, %s, %s)",this.itemCode, this.itemDescription, this.price, this.itemId, this.quantity, this.purchaseDate);
    }

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	

}
