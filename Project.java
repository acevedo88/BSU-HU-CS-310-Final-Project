/*
 * @author Alex Acevedo
 * 
 * CS310 Instructor Arthur Puthnam
 * July 3 2019
 * 
 * CS310 Final project that uses methods to call SQL commands that will create
 * a database along with three tables that will store values inputed through
 * command line.
 */

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Project {

	//Methods uses to create the items, purchases, and shipments based on the specified user input.
	//Opens sql connection and inserts values into a query code line which will execute and save results.
	
	//Creates Item
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

	//Creates  purchase
	public static Purchase createPurchase(String itemCode, int quantity) throws SQLException {
		
		Connection connection = null;
		Purchase purchase = new Purchase(itemCode, quantity);

		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();

		String sql = String.format("INSERT INTO Purchase (ItemID, Quantity) VALUES ((SELECT Item.ID FROM Item WHERE Item.ItemCode = '%s'),'%s');",
				itemCode, quantity);
		sqlStatement.executeUpdate(sql);
		connection.close();

		return purchase;


	}

	//Creates Shipment
	public static Shipment createShipment(String itemCode, int shipmentQuantity, String date) throws SQLException{
		Connection connection = null;
		Shipment shipment = new Shipment(itemCode, shipmentQuantity, date);

		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();

		String sql = String.format("INSERT INTO Shipment (ItemID, Quantity, ShipmentDate) VALUES ((SELECT Item.ID FROM Item WHERE Item.ItemCode = '%s'),'%s','%s');",
				itemCode, shipmentQuantity, date);
		sqlStatement.executeUpdate(sql);
		connection.close();

		return shipment;

	}


	//Create itmes using the stored procedure which will call a the procedure in the sql database

	public static Item createItemUsingStoredProcedure(String itemCode, String itemDescription, double price)
			throws SQLException {

		Connection connection = null;
		Item item = new Item(itemCode, itemDescription, price);


		connection = MySqlDatabase.getDatabaseConnection();

		String sql = String.format("CALL CreateNewItems('%s', '%s', '%s')", itemCode, itemDescription, price);
		PreparedStatement preparedStatement = connection.prepareStatement(sql);

		preparedStatement.setString(1, itemCode);
		preparedStatement.setString(2, itemDescription);
		preparedStatement.setDouble(3, price);

		preparedStatement.execute();
		connection.close();

		return item;
	}


	//Methods uses to get the items, purchases, and shipments based on the specified user input.
	//Opens sql connection and inserts values into a query code line which will execute and save results.
	
	//Gets the Items
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
	

	//Gets the Purchase based on ItemCode
	public static List<Purchase> getPurchase(String itemCode) throws SQLException {
		Connection connection = null;


		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();

		String sql = String.format("SELECT * FROM Item Join (SELECT ItemID, Quantity, PurchaseDate FROM Purchase) AS tbl_p ON Item.ID = tbl_p.ItemID WHERE ItemCode = '%s';",itemCode);
		ResultSet resultSet = sqlStatement.executeQuery(sql);

		List<Purchase> purchases = new ArrayList<Purchase>();

		while (resultSet.next()) {
			String itemCodes = resultSet.getString(2);
			String itemDescription = resultSet.getString(3);
			double price = resultSet.getDouble(4);
			int itemID = resultSet.getInt(5);
			int quantity = resultSet.getInt(6);
			String purchaseDate = resultSet.getString(7);
			Purchase purchase = new Purchase(itemCodes, itemDescription, price, itemID, quantity, purchaseDate);
			purchases.add(purchase);
		}
		resultSet.close();
		connection.close();
		return purchases;

	}
	
	//Gets the Shipments based on the ItemCode
	public static List<Shipment> getShipment(String itemCode) throws SQLException {
		Connection connection = null;


		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();

		String sql = String.format("SELECT * FROM Item Join (SELECT ItemID, Quantity, ShipmentDate FROM Shipment) AS tbl_s ON Item.ID = tbl_s.ItemID WHERE ItemCode = '%s';",itemCode);
		ResultSet resultSet = sqlStatement.executeQuery(sql);

		List<Shipment> shipments = new ArrayList<Shipment>();

		while (resultSet.next()) {
			String itemCodes = resultSet.getString(2);
			String itemDescription = resultSet.getString(3);
			double price = resultSet.getDouble(4);
			int itemID = resultSet.getInt(5);
			int quantity = resultSet.getInt(6);
			String shipmentDate = resultSet.getString(7);

			Shipment shipment = new Shipment(itemCodes, itemDescription, price, itemID, quantity, shipmentDate);
			shipments.add(shipment);
		}
		resultSet.close();
		connection.close();
		return shipments;

	}


	//Methods uses to get all the items, purchases, and shipments based on the specified user input.
	//Opens sql connection and inserts values into a query code line which will execute and save results.

	//Gets all the Items
	public static List<Item> getAllItems() throws SQLException {
		Connection connection = null;


		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();

		String sql = "SELECT * From Item;";
		ResultSet resultSet = sqlStatement.executeQuery(sql);

		List<Item> items = new ArrayList<Item>();

		while (resultSet.next()) {
			int id = resultSet.getInt(1);
			String itemCodes = resultSet.getString(2);
			String itemDescription = resultSet.getString(3);
			double price = resultSet.getDouble(4);

			Item item = new Item(id, itemCodes, itemDescription, price);
			items.add(item);

		}
		
		resultSet.close();
		connection.close();
		return items;

	}

	//Gets all the Purchases based on ItemCode
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
			String itemCode = resultSet.getString(2);
			String itemDescription = resultSet.getString(3);
			double price = resultSet.getDouble(4);
			int itemID = resultSet.getInt(5);
			int quantity = resultSet.getInt(6);
			String purchaseDate = resultSet.getString(7);

			Purchase purchase = new Purchase(itemCode, itemDescription, price, itemID, quantity, purchaseDate);
			purchases.add(purchase);	

		}
		resultSet.close();
		connection.close();
		return purchases;
	}

	//Gets all the Shipments based on ItemCode
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
			String itemCode = resultSet.getString(2);
			String itemDescription = resultSet.getString(3);
			double price = resultSet.getDouble(4);
			int itemID = resultSet.getInt(5);
			int quantity = resultSet.getInt(6);
			String shipmentDate = resultSet.getString(7);

			Shipment shipment = new Shipment(itemCode, itemDescription, price, itemID, quantity, shipmentDate);
			shipments.add(shipment);	
		}
		resultSet.close();
		connection.close();
		return shipments;
	}

	//Method used to Update an Item's Price based on the ItemCode
	public static void updateItem(String itemCode, double price) throws SQLException {
		Connection connection = null;

		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();

		String sql = String.format("UPDATE Item Set Item.Price = '%s' WHERE Item.ItemCode = '%s';",price, itemCode);

		sqlStatement.executeUpdate(sql);

		connection.close();
	}

	//Items Available method
	public static void availableItems(String itemCode) throws SQLException {
		Connection connection = null;
		
			connection = MySqlDatabase.getDatabaseConnection();
			String sql = String.format("CALL ItemsAvailable('%s')", itemCode);
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery(sql);
			int rows = 0;
			
			String itemhead = String.format("%-15s %-25s %-12s", "Item Code","Description","Quantity");
			System.out.println("_________________________________________________________");
			
			while(resultSet.next()) {
				rows++;
				String itemCodes = resultSet.getString(1);
				String itemDescriptions = resultSet.getString(2);
				int quantities = resultSet.getInt(3);
				String output = String.format("%-15s %-25s %-12s", itemCodes, itemDescriptions, quantities);
				System.out.println(itemhead);
				System.out.println(output);
			}
			
			System.out.println("\n\nThe Items Amounts: "+rows);
			connection.close();
			
			

	}
	
	
	//Methods used to Delete an Item, Purchase, or Shipment based on the ItemCode
	
	//Deletes an Item
	public static void deleteItem(String itemCode) throws SQLException {
		Connection connection = null;

		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();

		String sql = String.format("DELETE FROM Item WHERE ItemCode = '%s';", itemCode);
		sqlStatement.executeUpdate(sql);
		connection.close();
	}

	//Deletes a Purchase if it exists
	public static void deletePurchase(String itemCode) throws SQLException {
		Connection connection = null;

		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();


		String sql = String.format("SELECT ID FROM Purchase WHERE ItemID = ((SELECT Item.ID FROM Item WHERE Item.ItemCode = '%s')) ORDER BY PurchaseDate DESC LIMIT 1;", itemCode);
		ResultSet purchaseResult = sqlStatement.executeQuery(sql);
		
		purchaseResult.last();
		int rows = purchaseResult.getRow();
		purchaseResult.beforeFirst();
		int purchaseID;
		
		if(rows == 0) {
			System.out.println("No Item Codes were Found matching that result.");
		}
		
		else {
			purchaseResult.next();
			purchaseID = purchaseResult.getInt(1);
			sql = String.format("DELETE FROM Purchase WHERE ID = '%s';", purchaseID);
			int deletes = sqlStatement.executeUpdate(sql);
			System.out.println(deletes + " items were deleted.");
		}
		
		connection.close();
	}

	//Deletes a Shipment is it exists
	public static void deleteShipment(String itemCode) throws SQLException {
		Connection connection = null;

		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		String sql = String.format("SELECT ID FROM Shipment WHERE ItemID = ((SELECT Item.ID FROM Item WHERE Item.ItemCode = '%s')) ORDER BY ShipmentDate DESC LIMIT 1;", itemCode);
		ResultSet shipResult = sqlStatement.executeQuery(sql);
		
		shipResult.last();
		int rows = shipResult.getRow();
		shipResult.beforeFirst();
		int shipID;
		
		if(rows == 0) {
			System.out.println("No Item Codes were Found matching that result.");
		}
		
		else {
			shipResult.next();
			shipID = shipResult.getInt(1);
			sql = String.format("DELETE FROM Shipment WHERE ID = '%s';", shipID);
			int deletes = sqlStatement.executeUpdate(sql);
			System.out.println(deletes + " items were deleted.");
		}
		connection.close();
	}


	//Methods used to Attempt to Create, Delete, Update, and Get Items.
	//Will verify if operation work with a Try Catch block

	//Attempts to Create new Item
	public static void attemptToCreateNewItem(String itemCode, String itemDescription, double price) {
		try {
			Item item = createItem(itemCode, itemDescription, price);
			System.out.println(item.toString());
		} catch (SQLException sqlException) {
			System.out.println("Failed to create item");
			System.out.println(sqlException.getMessage());
		}

	}

	//Attempts to Create new Purchase
	public static void attemptToCreateNewPurchase(String itemCode, int quantity) {
		try {
			Purchase purchase = createPurchase(itemCode, quantity);
			System.out.println("Purchase created for Item Code "+itemCode+" "+"for the quantity of "+quantity+".");
		} catch (SQLException sqlException) {
			System.out.println("Failed to create purchase");
			System.out.println(sqlException.getMessage());
		}

	}
	
	//Attempts to Create new Shipment
	public static void attemptToCreateNewShipment(String itemCode, int shipmentQuantity, String shipmentDate) {
		try {
			Shipment shipment = createShipment(itemCode, shipmentQuantity, shipmentDate);
			System.out.println("Purchase created for Item Code "+itemCode+" "+" for the amount of "+shipmentQuantity+" on the date "+shipmentDate+".");
		} catch (SQLException sqlException) {
			System.out.println("Failed to create shipment");
			System.out.println(sqlException.getMessage());
		}

	}
	
	//Attempts to Get all Items
	public static void attemptToGetAllItems() {
		try {
			List<Item> Newitems = getAllItems();
			for (Item item : Newitems) {
				System.out.println(item.toString());
			}
		} catch (SQLException sqlException) {
			System.out.println("Failed to get all Items");
			System.out.println(sqlException.getMessage());
		}
	}
	
	//Attempts to get an Item based on ItemCode
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
	
	//Attempts to get all Purchases
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
	
	//Attempts to get a Purchase based on ItemCode
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
	
	//Attempts to get all Shipments 
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
	
	//Attempts to get a Shipment based on ItemCode
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
	
	//Attempts to Update an Item based on ItemCode
	public static void attemptToUpdateItem(String itemCode, double price) {
        try {
            updateItem(itemCode, price);
        } catch (SQLException sqlException) {
            System.out.println("Failed to update Item");
            System.out.println(sqlException.getMessage());
        }

    }
	
	//Attempts to Delete an Item based on ItemCode
	public static void attemptToDeleteItem(String itemCode) {
        try {
            deleteItem(itemCode);
        } catch (SQLException sqlException) {
            System.out.println("Failed to Delete Item");
            System.out.println(sqlException.getMessage());
        }

    }
	
	//Attempts to Delete a Purchase based on ItemCode
	public static void attemptToDeletePurchase(String itemCode) {
        try {
            deletePurchase(itemCode);
        } catch (SQLException sqlException) {
            System.out.println("Failed to Delete Purchase");
            System.out.println(sqlException.getMessage());
        }

    }
	
	//Attempts to Delete a Shipment based on ItemCode
	public static void attemptToDeleteShipment(String itemCode) {
        try {
            deleteShipment(itemCode);
        } catch (SQLException sqlException) {
            System.out.println("Failed to Delete Shipment");
            System.out.println(sqlException.getMessage());
        }

    }
	
	//Attempts to get Available Items based on ItemCode using a stored procedure
	public static void attemptToGetAvailableItems(String itemCode) {
		try {
            availableItems(itemCode);
        } catch (SQLException sqlException) {
            System.out.println("Failed to get Available Items");
            System.out.println(sqlException.getMessage());
        }
	}
	
	//Attempts to get Create Items based on ItemCode, Description and price using a stored procedure
	public static void attemptToCreateItemFromProcedure(String itemCode, String itemDescription, double price) {
		try {
			 createItemUsingStoredProcedure(itemCode, itemDescription, price);
		}
		catch(SQLException sqlException) {
			  System.out.println("Failed to get Create Items Using the Store Procedure");
	           System.out.println(sqlException.getMessage());
		}
	}


	//Wrong input template
	public static void wrongFormat() {
		
		System.out.println("\nSorry that is an incorrect format.  Please use one of the following commands for your input: ");
		System.out.println("CreateItem <itemCode> <itemDescription> <price>"+"\n"+"CreatePurchase <itemCode> <PurchaseQuantity>"
				+"\n"+"CreateShipment <itemCode> <ShipmentQuantity> <shipmentDate>" +"\n"+"GetItems <itemCode>"
				+"\n"+"GetShipments <itemCode>" +"\n"+"GetPurchases <itemCode>" +"\n"+"ItemsAvailable <itemCode>" 
				+"\n"+"UpdateItem <itemCode> <price>"+"\n"+"DeleteItem <itemCode>" +"\n"+"DeleteShipment <itemCode>"
				+"\n"+"DeletePurchase <itemCode>"+"\nCreateItemFromProcedure <itemCode> <itemDescription> <price>");
	}

	
	// Main Method that will use command line arguments to select which operations
	// To implement correctly.  Will execute wrongFormat() method if inputed incorrectly.

	public static void main(String[] args) throws SQLException{


		if (args[0].equals("CreateItem")) {
			String itemCode = args[1];
			String itemDescription = args[2];
			double price = Double.parseDouble(args[3]);
			attemptToCreateNewItem(itemCode, itemDescription, price);
		}
		else if(args[0].equals("CreatePurchase")){
			String itemCode = args[1];
			int quantity = Integer.parseInt(args[2]);
			attemptToCreateNewPurchase(itemCode, quantity);

		}
		else if(args[0].equals("CreateShipment")){
			String itemCode = args[1];
			int shipmentQuantity = Integer.parseInt(args[2]);
			String shipmentDate = args[3];
			attemptToCreateNewShipment(itemCode, shipmentQuantity, shipmentDate);

		}
		else if(args[0].equals("GetItems")){
			String itemCode = args[1];
			if(itemCode.equals("%")) {
				
				attemptToGetAllItems();
			}
			else {
				attemptToGetItem(args[1]);
			}

		}
		else if(args[0].equals("GetPurchases")) {
			String itemCode = args[1];
			if(itemCode.equals("%")){
				attemptToGetAllPurchases();
			}
			else {
				attemptToGetPurchases(args[1]);
			}
		}
		else if(args[0].equals("GetShipments")) {
			String itemCode = args[1];
			if(itemCode.equals("%")){
				attemptToGetAllShipments();
			}
			else {
				attemptToGetShipments(args[1]);
			}
		}
		else if(args[0].equals("UpdateItem")) {
			String itemCode = args[1];
            double price = Double.parseDouble(args[2]);
            attemptToUpdateItem(itemCode, price);
		}
		else if(args[0].equals("DeleteItem")) {
			String itemCode = args[1];
            attemptToDeleteItem(itemCode);
            System.out.println("Item "+itemCode+" was deleted.");
		}
		else if(args[0].equals("DeletePurchase")) {
			String itemCode = args[1];
            attemptToDeletePurchase(itemCode);
		}
		else if(args[0].equals("DeleteShipment")) {
			String itemCode = args[1];
            attemptToDeleteShipment(itemCode);
		}
		else if(args[0].equals("ItemsAvailable")) {
			String itemCode = args[1];
			if(itemCode.equals("%")) {
				
				attemptToGetAvailableItems(itemCode);
			}
			else {
				attemptToGetAvailableItems(itemCode);
			}
		}
		
		else {
			wrongFormat();
		}


	}


}
