public class Purchase {
	private int itemId;
	private int quantity;
	private String purchaseDate;
	
	public Purchase(int itemID, int quantity, String purchaseDate) {
        this.itemId = itemID;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
        
    }
	
	public Purchase(int itemID, int quantity) {
        this.itemId = itemID;
        this.quantity = quantity;
        
    }
	
    public String toString(){
        return String.format("(%s, %s, %s)", this.itemId, this.quantity, this.purchaseDate);
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
	
	

}
