
Create table Purchase(ID int auto_increment 
, ItemID int  NOT NULL
, Quantity int NOT NULL
, PurchaseDate datetime DEFAULT current_timestamp
, primary key (id)
, FOREIGN KEY (ItemID) REFERENCES Item(ID));