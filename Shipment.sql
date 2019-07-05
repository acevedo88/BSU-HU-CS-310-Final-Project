
Create table Shipment(ID int auto_increment 
, ItemID int  NOT NULL
, Quantity int NOT NULL
, ShipmentDate datetime UNIQUE NOT NULL
, primary key (id)
, FOREIGN KEY (ItemID) REFERENCES Item(ID));