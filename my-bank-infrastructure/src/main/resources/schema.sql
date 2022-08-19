CREATE TABLE customers (
    id INTEGER  NOT NULL AUTO_INCREMENT,
    customer_code UUID UNIQUE NOT NULL,
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

CREATE TABLE bank_accounts (
    id INTEGER NOT NULL AUTO_INCREMENT,
    account_code UUID UNIQUE NOT NULL,
    account_number VARCHAR(50) UNIQUE NOT NULL,
    consolidated_balance FLOAT NOT NULL,
    interval_balance FLOAT NOT NULL,
    alias VARCHAR(50),
    default_currency VARCHAR(10) NOT NULL,
    customer_code UUID NOT NULL,
    start_interval DATE NOT NULL,
    end_interval DATE NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE bank_accounts
  ADD FOREIGN KEY (customer_code)
  REFERENCES customers(customer_code);

--Rethink solution about this
CREATE TABLE transactions (
   id INTEGER NOT NULL AUTO_INCREMENT,
   transaction_code UUID NOT NULL,
   discriminator_account_code UUID NOT NULL,
   origin_account_code UUID NOT NULL,
   target_account_code UUID NOT NULL,
   amount FLOAT NOT NULL,
   concept VARCHAR(45) NOT NULL,
   transaction_date DATE NOT NULL,
   PRIMARY KEY (id)
);

ALTER TABLE transactions
  ADD FOREIGN KEY (origin_account_code)
  REFERENCES bank_accounts(account_code);

ALTER TABLE transactions
  ADD FOREIGN KEY (target_account_code)
  REFERENCES bank_accounts(account_code);