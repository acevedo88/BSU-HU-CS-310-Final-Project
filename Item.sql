
Create table Item(ID int auto_increment 
, ItemCode varchar(10) NOT NULL UNIQUE
, ItemDescription varchar(50)
, Price decimal(4,2) default 0
, primary key (id));