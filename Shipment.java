public class Shipment {
	private int itemId;
	private int quantity;
	private String shipmentDate;
	private String itemCode;
	private String itemDescription;
	private double price;
	private int shipmentQuantity;
	private String date;
	
	public Shipment(String itemCode, String itemDescription, double price, int itemID, int quantity, String purchaseDate) {
		this.itemCode = itemCode;
		this.itemDescription = itemDescription;
		this.price = price;
        this.itemId = itemID;
        this.quantity = quantity;
        this.shipmentDate = purchaseDate;
        
    }
	public Shipment(int itemID, int quantity, String shipmentDate) {
        this.itemId = itemID;
        this.quantity = quantity;
        this.shipmentDate = shipmentDate;
        
    }
	
	public Shipment(int itemID, int quantity) {
        this.itemId = itemID;
        this.quantity = quantity;
        
    }
	public Shipment(String itemCode, int shipmentQuantity, String date) {
        this.itemCode = itemCode;
        this.shipmentQuantity = shipmentQuantity;
        this.date = date;
        
    }
	
	public String toString(){
        return String.format("(%s, %s, %s, %s, %s, %s)",this.itemCode, this.itemDescription, this.price, this.itemId, this.quantity, this.shipmentDate);
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

	public String getShipmentDate() {
		return shipmentDate;
	}

	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
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
	public int getShipmentQuantity() {
		return shipmentQuantity;
	}
	public void setShipmentQuantity(int shipmentQuantity) {
		this.shipmentQuantity = shipmentQuantity;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	

}
