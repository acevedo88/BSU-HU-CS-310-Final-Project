
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class FinalProject {

	//Create methods

	public static Item createItem(String itemCode, String itemDescription, double price) throws SQLException {
		Connection connection = null;
		Item item = new Item(itemCode, itemDescription, price);

		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();

		String sql = String.format("INSERT INTO Item (ItemCode, ItemDescription, Price) VALUES ('%s' , '%s', %s);",
				item.getItemCode(),
				item.getItemDescription(),
				item.getPrice());
		sqlStatement.executeUpdate(sql);
		connection.close();

		return item;
	}

	public static Purchase createPurchase(int itemID, int quantity) throws SQLException {
		Connection connection = null;


		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();

		String sql = String.format("INSERT INTO Purchase (ItemID, Quantity, PurchaseDate) VALUES ('%s' , '%s' , '%s');",
				itemID, quantity);

		sqlStatement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

		ResultSet resultSet = sqlStatement.getGeneratedKeys();
		resultSet.next();

		int owner_id = resultSet.getInt(1);
		connection.close();

		return new Purchase(owner_id, quantity);


	}

	public static Shipment createShipment(int itemID, int quantity, String shipmentDate) throws SQLException{
		Connection connection = null;

		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();

		String sql = String.format("INSERT INTO Shipment (ItemID, Quantity, ShipmentDate) VALUES ('%s', '%s' , '%s' );",
				itemID, quantity, shipmentDate);

		sqlStatement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

		ResultSet resultSet = sqlStatement.getGeneratedKeys();
		resultSet.next();

		int owner_id = resultSet.getInt(1);
		connection.close();


		return new Shipment(owner_id, quantity, shipmentDate);

	}


	//Create using the stored procedure

	public static Item createItemUsingStoredProcedure(String itemCode, String itemDescription, double price, int itemId)
			throws SQLException {

		Connection connection = null;
		Item item = new Item(itemCode, itemDescription, price);


		connection = MySqlDatabase.getDatabaseConnection();
		//        create procedure for here
		String sql = "CALL create_Item(?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);

		preparedStatement.setString(1, itemCode);
		preparedStatement.setString(2, itemDescription);
		preparedStatement.setDouble(3, price);

		preparedStatement.execute();
		connection.close();

		return item;
	}


	//Get methods


	public static List<Item> getItems(String itemCode) throws SQLException {
		Connection connection = null;


		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();

		String sql = String.format("SELECT * From Item WHERE ItemCode = '%s';",itemCode);
		ResultSet resultSet = sqlStatement.executeQuery(sql);

		List<Item> items = new ArrayList<Item>();

		while (resultSet.next()) {
			String itemCodes = resultSet.getString(2);
			String itemDescription = resultSet.getString(3);
			double price = resultSet.getDouble(4);

			Item item = new Item(itemCodes, itemDescription, price);
			items.add(item);
		}
		resultSet.close();
		connection.close();
		return items;

	}
	public static List<Purchase> getPurchase(String itemCode) throws SQLException {
		Connection connection = null;


		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();

		String sql = String.format("SELECT * FROM Item Join (SELECT * FROM Purchase) AS tbl_p ON Item.ID = tbl_p.ItemID WHERE ItemCode = '%s';",itemCode);
		ResultSet resultSet = sqlStatement.executeQuery(sql);

		List<Purchase> purchases = new ArrayList<Purchase>();

		while (resultSet.next()) {
			int itemID = resultSet.getInt(2);
			int quantity = resultSet.getInt(3);
			String purchaseDate = resultSet.getString(4);

			Purchase purchase = new Purchase(itemID, quantity, purchaseDate);
			purchases.add(purchase);
		}
		resultSet.close();
		connection.close();
		return purchases;

	}
	public static List<Shipment> getShipment(String itemCode) throws SQLException {
		Connection connection = null;


		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();

		String sql = String.format("SELECT * FROM Item Join (SELECT * FROM Shipment) AS tbl_s ON Item.ID = tbl_s.ItemID WHERE ItemCode = '%s';",itemCode);
		ResultSet resultSet = sqlStatement.executeQuery(sql);

		List<Shipment> shipments = new ArrayList<Shipment>();

		while (resultSet.next()) {
			int itemID = resultSet.getInt(2);
			int quantity = resultSet.getInt(3);
			String shipmentDate = resultSet.getString(4);

			Shipment shipment = new Shipment(itemID, quantity, shipmentDate);
			shipments.add(shipment);
		}
		resultSet.close();
		connection.close();
		return shipments;

	}


	//Get all methods

	public static List<Item> getAllItems() throws SQLException {
		Connection connection = null;


		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();

		String sql = "SELECT * FROM Item;";
		ResultSet resultSet = sqlStatement.executeQuery(sql);

		List<Item> items = new ArrayList<Item>();

		while (resultSet.next()) {
			String itemCode = resultSet.getString(2);
			String itemDescription = resultSet.getString(3);
			double price = resultSet.getDouble(4);

			Item item = new Item(itemCode, itemDescription, price);
			items.add(item);
		}
		resultSet.close();
		connection.close();
		return items;

	}

	public static List<Purchase> getAllPurchases() throws SQLException{
		Connection connection = null;

		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();

		String sql = "SELECT Item.ID, Item.ItemCode, Item.ItemDescription, Item.Price\n" + 
				",Purchase.ItemID, Purchase.Quantity, Purchase.PurchaseDate\n" + 
				"FROM Item,Purchase WHERE Item.ID = Purchase.ItemID;";
		ResultSet resultSet = sqlStatement.executeQuery(sql);

		List<Purchase> purchases = new ArrayList<Purchase>();

		while (resultSet.next()) {
			int itemID = resultSet.getInt(2);
			int quantity = resultSet.getInt(3);
			String purchaseDate = resultSet.getString(4);

			Purchase purchase = new Purchase(itemID, quantity, purchaseDate);
			purchases.add(purchase);	
		}
		resultSet.close();
		connection.close();
		return purchases;
	}

	public static List<Shipment> getAllShipments() throws SQLException{
		Connection connection = null;

		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();

		String sql = "SELECT Item.ID, Item.ItemCode, Item.ItemDescription, Item.Price\n" + 
				",Shipment.ItemID, Shipment.Quantity, Shipment.ShipmentDate\n" + 
				"FROM Item,Shipment WHERE Item.ID = Shipment.ItemID;";
		ResultSet resultSet = sqlStatement.executeQuery(sql);

		List<Shipment> shipments = new ArrayList<Shipment>();

		while (resultSet.next()) {
			int itemID = resultSet.getInt(2);
			int quantity = resultSet.getInt(3);
			String shipmentDate = resultSet.getString(4);

			Shipment shipment = new Shipment(itemID, quantity, shipmentDate);
			shipments.add(shipment);	
		}
		resultSet.close();
		connection.close();
		return shipments;
	}

	//Update Item method

	public static void updateItem(String itemCode, double price) throws SQLException {
		Connection connection = null;

		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();

		String sql = String.format("UPDATE Item SET ItemCode = '%s', Price = '%s' WHERE ItemCode = %s;",
				itemCode, price);

		sqlStatement.executeUpdate(sql);

		connection.close();
	}

	//Delete methods

	public static void deleteItem(String itemCode) throws SQLException {
		Connection connection = null;

		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();

		String sql = String.format("DELETE FROM Item WHERE ItemCode = %s;", itemCode);
		sqlStatement.executeUpdate(sql);
		connection.close();
	}

	public static void deletePurchase(String itemCode) throws SQLException {
		Connection connection = null;

		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();


		String sql = String.format("SET @Ptop = (SELECT p.ID  FROM Purchase AS p LEFT JOIN Item as i ON p.ItemID = i.ID WHERE i.ItemCode = '%s' ORDER BY PurchaseDate DESC Limit 1);DELETE FROM Purchase WHERE ID = @Ptop;", itemCode);
		sqlStatement.executeUpdate(sql);
		connection.close();
	}

	public static void deleteShipment(String itemCode) throws SQLException {
		Connection connection = null;

		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();


		String sql = String.format("Set @SHtop = (SELECT s.ID FROM Shipment as s LEFT JOIN Item as i on s.ItemID = i.ID WHERE i.ItemCode = '%s' ORDER BY ShipmentDate DESC LIMIT 1); Delete FROM Shipment WHERE ID = @SHtop;", itemCode);
		sqlStatement.executeUpdate(sql);
		connection.close();
	}


	//Attempts to create methods

	public static void attemptToCreateNewItem(String itemCode, String itemDescription, double price) {
		try {
			Item item = createItem(itemCode, itemDescription, price);
			System.out.println(item.toString());
		} catch (SQLException sqlException) {
			System.out.println("Failed to create item");
			System.out.println(sqlException.getMessage());
		}

	}

	public static void attemptToCreateNewPurchase(int itemID, int quantity) {
		try {
			Purchase purchase = createPurchase(itemID, quantity);
			System.out.println(purchase.toString());
		} catch (SQLException sqlException) {
			System.out.println("Failed to create purchase");
			System.out.println(sqlException.getMessage());
		}

	}
	public static void attemptToCreateNewShipment(int itemID, int quantity, String shipmentDate) {
		try {
			Shipment shipment = createShipment(itemID, quantity, shipmentDate);
			System.out.println(shipment.toString());
		} catch (SQLException sqlException) {
			System.out.println("Failed to create shipment");
			System.out.println(sqlException.getMessage());
		}

	}
	public static void attemptToGetAllItems() {
		try {
			List<Item> items = getAllItems();
			for (Item item : items) {
				System.out.println(item.toString());
			}
		} catch (SQLException sqlException) {
			System.out.println("Failed to get all Items");
			System.out.println(sqlException.getMessage());
		}
	}
	public static void attemptToGetItem(String itemCode) {
		try {
			List<Item> items = getItems(itemCode);
			for (Item item : items) {
				System.out.println(item.toString());
			}
		} catch (SQLException sqlException) {
			System.out.println("Failed to get Items");
			System.out.println(sqlException.getMessage());
		}
	}
	public static void attemptToGetAllPurchases() {
		try {
			List<Purchase> purchases = getAllPurchases();
			for (Purchase purchase : purchases) {
				System.out.println(purchase.toString());
			}
		} catch (SQLException sqlException) {
			System.out.println("Failed to get all Purchases");
			System.out.println(sqlException.getMessage());
		}
	}
	public static void attemptToGetPurchases(String itemCode) {
		try {
			List<Purchase> purchases = getPurchase(itemCode);
			for (Purchase purchase : purchases) {
				System.out.println(purchase.toString());
			}
		} catch (SQLException sqlException) {
			System.out.println("Failed to get Purchases");
			System.out.println(sqlException.getMessage());
		}
	}
	public static void attemptToGetAllShipments() {
		try {
			List<Shipment> shipments = getAllShipments();
			for (Shipment shipment : shipments) {
				System.out.println(shipment.toString());
			}
		} catch (SQLException sqlException) {
			System.out.println("Failed to get all Shipments");
			System.out.println(sqlException.getMessage());
		}
	}
	public static void attemptToGetShipments(String itemCode) {
		try {
			List<Shipment> shipments = getShipment(itemCode);
			for (Shipment shipment : shipments) {
				System.out.println(shipment.toString());
			}
		} catch (SQLException sqlException) {
			System.out.println("Failed to get Shipments");
			System.out.println(sqlException.getMessage());
		}
	}
	public static void attemptToUpdateItem(String itemCode, double price) {
        try {
            updateItem(itemCode, price);
        } catch (SQLException sqlException) {
            System.out.println("Failed to update Item");
            System.out.println(sqlException.getMessage());
        }

    }
	public static void attemptToDeleteItem(String itemCode) {
        try {
            deleteItem(itemCode);
        } catch (SQLException sqlException) {
            System.out.println("Failed to Delete Item");
            System.out.println(sqlException.getMessage());
        }

    }
	public static void attemptToDeletePurchase(String itemCode) {
        try {
            deletePurchase(itemCode);
        } catch (SQLException sqlException) {
            System.out.println("Failed to Delete Purchase");
            System.out.println(sqlException.getMessage());
        }

    }
	public static void attemptToDeleteShipment(String itemCode) {
        try {
            deleteShipment(itemCode);
        } catch (SQLException sqlException) {
            System.out.println("Failed to Delete Shipment");
            System.out.println(sqlException.getMessage());
        }

    }



	// Main

	public static void main(String[] args) throws SQLException{


		if (args[0].equals("CreateItem")) {
			String itemCode = args[1];
			String itemDescription = args[2];
			double price = Double.parseDouble(args[3]);
			attemptToCreateNewItem(itemCode, itemDescription, price);
		}
		else if(args[0].equals("CreatePurchase")){
			int itemID = Integer.parseInt(args[1]);
			int quantity = Integer.parseInt(args[2]);
			attemptToCreateNewPurchase(itemID, quantity);

		}
		else if(args[0].equals("CreateShipment")){
			int itemID = Integer.parseInt(args[1]);
			int quantity = Integer.parseInt(args[2]);
			String shipmentDate = args[3];
			attemptToCreateNewShipment(itemID, quantity, shipmentDate);

		}
		else if(args[0].equals("GetItems")){
			String itemCode = args[1];
			if(itemCode == "%") {
				attemptToGetAllItems();
			}
			else {
				attemptToGetItem(args[1]);
			}

		}
		else if(args[0].equals("GetPurchases")) {
			String itemCode = args[1];
			if(itemCode == "%"){
				attemptToGetAllPurchases();
			}
			else {
				attemptToGetPurchases(args[1]);
			}
		}
		else if(args[0].equals("GetShipments")) {
			String itemCode = args[1];
			if(itemCode == "%"){
				attemptToGetAllShipments();
			}
			else {
				attemptToGetShipments(args[1]);
			}
		}
		else if(args[0].equals("UpdateItem")) {
			String itemCode = args[2];
            double price = Double.parseDouble(args[3]);
            attemptToUpdateItem(itemCode, price);
		}
		else if(args[0].equals("DeleteItem")) {
			String itemCode = args[2];
            attemptToDeleteItem(itemCode);
		}
		else if(args[0].equals("DeletePurchase")) {
			String itemCode = args[2];
            attemptToDeletePurchase(itemCode);
		}
		else if(args[0].equals("DeleteShipment")) {
			String itemCode = args[2];
            attemptToDeleteShipment(itemCode);
		}
		
		else {
			System.out.println("Sorry that is an incorrect format.  Please use one of the following commands for your input: ");
			System.out.println("CreateItem <itemCode> <itemDescription> <price>"+"\n"+"CreatePurchase <itemCode> <PurchaseQuantity>"
					+"\n"+"CreateShipment <itemCode> <ShipmentQuantity> <shipmentDate>" +"\n"+"GetItems <itemCode>"
					+"\n"+"GetShipments <itemCode>" +"\n"+"GetPurchases <itemCode>" +"\n"+"ItemsAvailable <itemCode>" 
					+"\n"+"UpdateItem <itemCode> <price>"+"\n"+"DeleteItem <itemCode>" +"\n"+"DeleteShipment <itemCode>"
					+"\n"+"DeletePurchase <itemCode>");
		}


	}


}
