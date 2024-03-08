CREATE DATABASE IF NOT EXISTS kailua_car_rental;
USE kailua_car_rental;

-- Create Model
CREATE TABLE IF NOT EXISTS car_model (
    model_id				INT				NOT NULL	PRIMARY KEY		AUTO_INCREMENT,
    brand					VARCHAR(20)		NOT NULL,
    model					VARCHAR(20)		NOT NULL
);

-- Create Fuel Type
CREATE TABLE IF NOT EXISTS fuel_type (
    fuel_type_name			VARCHAR(20)		NOT NULL	PRIMARY KEY
);

-- Create Car Type
CREATE TABLE IF NOT EXISTS car_type (
    car_type_name			VARCHAR(20)		NOT NULL	PRIMARY KEY
);

-- Create Car
CREATE TABLE IF NOT EXISTS car (
    car_id                  INT				NOT NULL	PRIMARY KEY		AUTO_INCREMENT,
    model_id				INT				NOT NULL	REFERENCES model(model_id),
    fuel_type_name			VARCHAR(20)		NOT NULL	REFERENCES fuel_type(fuel_type_name),
    car_type_name			VARCHAR(20)		NOT NULL	REFERENCES car_type(car_type_name),
    registration_number     VARCHAR(7)		NOT NULL,
    first_registration_date 	DATE			NOT NULL,
    mileage                 INT				NOT NULL
);

-- Create Postal Code
CREATE TABLE IF NOT EXISTS postal_code (
    postal_code    			INT				NOT NULL 	PRIMARY KEY,
    city           			VARCHAR(100)	NOT NULL
);

-- Create Customer
CREATE TABLE IF NOT EXISTS customer (
    customer_id    			INT				NOT NULL	PRIMARY KEY		AUTO_INCREMENT,
    first_name     			VARCHAR(100)	NOT NULL,
    last_name      			VARCHAR(100)	NOT NULL,
    address					VARCHAR(255)	NOT NULL,
    postal_code     		INT				NOT NULL	REFERENCES postal_code(postal_code),
    mobile_phone   			VARCHAR(12)		NOT NULL,
    phone_number   			VARCHAR(12)		DEFAULT NULL,
    email          			VARCHAR(60)		NOT NULL,
    license_number 			VARCHAR(20)		NOT NULL,
    issue_date     			DATE			NOT NULL
);

-- Create Rental
CREATE TABLE IF NOT EXISTS rental (
    rental_id		 		INT				NOT NULL	PRIMARY KEY		AUTO_INCREMENT,
    customer_id	 			INT				NOT NULL	REFERENCES customer(customer_id),
    car_id 		 			INT				NOT NULL	REFERENCES car(car_id),
    from_date		 		DATETIME		NOT NULL,
    to_date     	 		DATETIME		NOT NULL,
    max_km      	 		INT				NOT NULL,
    current_km  	 		INT				NOT NULL
);

-- DATA INSERT --

-- Insert Car Models
INSERT INTO car_model (brand, model)
VALUES
    ('Toyota', 'Corolla'),
    ('BMW', 'i8'),
    ('Tesla', 'Model S'),
    ('Ford', 'Focus'),
    ('BMW', 'M3');

-- Insert Fuel Types
INSERT INTO fuel_type (fuel_type_name)
VALUES
    ('GASOLINE'),
    ('DIESEL'),
    ('ELECTRIC'),
    ('HYBRID');

-- Insert Car Types
INSERT INTO car_type (car_type_name)
VALUES
    ('FAMILY'),
    ('SPORT'),
    ('LUXURY');

-- Insert Cars
INSERT INTO car (model_id, car_type_name, fuel_type_name, registration_number, first_registration_date, mileage)
VALUES
    (1, 'FAMILY', 'GASOLINE', 'TZ92995', '2022-06-15', 70000),
    (2, 'SPORT', 'HYBRID', 'JI80066', '2021-05-20', 15000),
    (3, 'LUXURY', 'ELECTRIC', 'JG92299', '2023-01-10', 2000),
    (4, 'FAMILY', 'DIESEL', 'KW12345', '2020-04-12', 25000);

-- Insert Postal Codes
INSERT INTO postal_code (postal_code, city)
VALUES
    (5270, 'Odense N'),
    (2650, 'Hvidovre'),
    (2200, 'København N'),
    (5230, 'Odense M');

-- Insert Customer
INSERT INTO customer (first_name, last_name, address, postal_code, mobile_phone, phone_number, email, license_number, issue_date)
VALUES
    ('John', 'Doe', 'Slotsgade 12', 5270, '+4512345678', '+4511122334', 'john.doe@email.com', 'ABC123456', '2021-07-15'),
    ('Jane', 'Smith', 'Rebæk Søpark 10', 2650, '+4598765432', '+4587654321', 'jane.smith@email.com', 'XYZ789012', '2020-11-25'),
    ('Bob', 'Johnson', 'Guldbergsgade 29N', 2200, '+4578901234', '+4567890123', 'bob.johnson@email.com', 'DEF456789', '2019-04-08'),
    ('Alice', 'Williams', 'Langelinie 14', 5230, '+4512345670', '+4511122335', 'alice.williams@email.com', 'GHI789012', '2022-02-28');

-- Insert Rental
INSERT INTO rental (customer_id, car_id, from_date, to_date, max_km, current_km)
VALUES
    (1, 1, '2022-03-01 08:00:00', '2022-03-10 12:00:00', 1000, 200),
    (2, 4, '2022-02-15 10:30:00', '2022-02-20 15:45:00', 500, 150),
    (3, 3, '2022-03-05 14:00:00', '2022-03-15 17:30:00', 1200, 300),
    (4, 2, '2022-02-28 09:15:00', '2022-03-08 18:00:00', 800, 180);