CREATE DEFINER=`root`@`localhost` PROCEDURE `CreateNewItems`(IN _ItemCode varchar(10), IN _ItemDescription varchar(50), IN _Price double)
BEGIN
INSERT INTO Item (ItemCode, ItemDescription, Price) VALUES (_ItemCode, _ItemDescription, _Price);
END