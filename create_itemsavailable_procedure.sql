CREATE DEFINER=`root`@`localhost` PROCEDURE `ItemsAvailable`(IN purchaseItemCode varchar(10))
BEGIN
 IF(purchaseItemCode = '%') THEN
 SELECT Item.ItemCode,
		Item.ItemDescription,
        IFNULL((SELECT SUM(Quantity) FROM Shipment WHERE Shipment.ItemID = Item.ID),0) - IFNULL((SELECT SUM(Quantity) FROM Purchase 
        WHERE Purchase.ItemID = Item.ID),0) AS have FROM Item;
        
ELSE
SELECT Item.ItemCode,
	   Item.ItemDescription,
       IFNULL((SELECT SUM(Quantity) FROM Shipment WHERE Shipment.ItemID = Item.ID),0) - IFNULL((SELECT SUM(Quantity) FROM Purchase 
        WHERE Purchase.ItemID = Item.ID),0) AS have FROM Item WHERE Item.ItemCode = purchaseItemCode;
        
END IF;



END