-- CREATE DATABASE 'shensang';

-- USE 'shensang'

CREATE TABLE `line_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product` varchar(64) NOT NULL,
  `unit_price` decimal(5,2) NOT NULL,
  `discount` decimal(4,2) DEFAULT '1.00',
  `quantity` int NOT NULL,
  `order_id` int NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
);

CREATE TABLE `order` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `order_date` date NOT NULL,
  `customer_name` varchar(128) NOT NULL,
  `ship_address` varchar(128) NOT NULL,
  `notes` text,
  `tax` decimal(2,2) NOT NULL DEFAULT '0.05',
  PRIMARY KEY (`order_id`)
);