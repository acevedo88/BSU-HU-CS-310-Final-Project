public class Shipment {
	private int itemId;
	private int quantity;
	private String shipmentDate;
	
	public Shipment(int itemID, int quantity, String shipmentDate) {
        this.itemId = itemID;
        this.quantity = quantity;
        this.shipmentDate = shipmentDate;
        
    }
	
	public Shipment(int itemID, int quantity) {
        this.itemId = itemID;
        this.quantity = quantity;
        
    }
	
	public String toString(){
        return String.format("(%s, %s, %s)", this.itemId, this.quantity, this.shipmentDate);
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
	
	

}
