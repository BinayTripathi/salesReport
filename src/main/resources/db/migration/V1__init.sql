--------------------------------------
-- Customer table creating and seeding
--------------------------------------
CREATE TABLE customer (
    customer_id INT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL
);

INSERT INTO customer (customer_id, first_name, last_name, email, location) VALUES
(10001,'Tony', 'Stark', 'tony.stark@gmail.com', 'Australia'),
(10002,'Bruce', 'Banner', 'bruce.banner@gmail.com', 'US'),
(10003,'Steve', 'Rogers', 'steve.rogers@hotmail.com', 'Australia'),
(10004,'Wanda', 'Maximoff', 'wanda.maximoff@gmail.com', 'US'),
(10005,'Natasha', 'Romanoff', 'natasha.romanoff@gmail.com', 'Canada');


-----------------------------------------
-- Product table creating and seeding
-----------------------------------------
CREATE TABLE product (
   product_code VARCHAR(255) PRIMARY KEY,
   cost INT NOT NULL,
   status VARCHAR(255) NOT NULL
);

INSERT INTO product (product_code, cost, status) VALUES
('PRODUCT_001', 50,  'Active'),
('PRODUCT_002', 100, 'Inactive'),
('PRODUCT_003', 200, 'Active'),
('PRODUCT_004', 10, 'Inactive'),
('PRODUCT_005', 500, 'Active');



----------------------------------------------------------------
-- Transaction table creation. Data will be pushed per API call
---------------------------------------------------------------
CREATE TABLE transaction (
  id INT AUTO_INCREMENT PRIMARY KEY,
  transaction_time TIMESTAMP NOT NULL,
  customer_id BIGINT NOT NULL,
  quantity INT NOT NULL,
  product_code VARCHAR(255) NOT NULL,
  cost FLOAT NOT NULL
);

CREATE SEQUENCE TRANSACTION_SEQ START WITH 1 INCREMENT BY 50;