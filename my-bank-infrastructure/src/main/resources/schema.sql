CREATE TABLE customers (
    id INTEGER  NOT NULL AUTO_INCREMENT,
    customer_code UUID NOT NULL,
    firstname VARCHAR(45) NOT NULL,
    lastname VARCHAR(45) NOT NULL,
    street VARCHAR(150) NOT NULL,
    city VARCHAR(150) NOT NULL,
    postal_code INTEGER NOT NULL,
    email VARCHAR(45) NOT NULL,
    birthdate DATE NOT NULL,
    password VARCHAR(250) NOT NULL,
    PRIMARY KEY (id)
);