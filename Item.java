public class Item {
    private String itemCode;
    private String itemDescription;
    private double price;
	private int id;

    public Item(int id, String itemCode, String itemDescription, double price) {
    	this.id = id;
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.price = price;
    }
    public Item(String itemCode, String itemDescription, double price) {
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.price = price;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public double getPrice() {
        return price;
    }


    public String toString(){
        return String.format("(%s, %s, %s, %s)",this.id, this.itemCode, this.itemDescription, this.price);
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public void setPrice(double price) {
		this.price = price;
	}
    
}