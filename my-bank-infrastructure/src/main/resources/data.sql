---- Customers
INSERT INTO customers (customer_code, firstname, lastname, street, city, postal_code, email, birthdate, password)
VALUES ('312ace91-3882-4751-9f4b-9c79dd925df2', 'Joe', 'Cocker', 'Street 12', 'Ohio', 410, 'joe@mail.com', '1954-03-21',
    '$2a$12$GFQGjnUJX/P7T7WDOLcx5uonSYsXCGC906WbUJlPVJ77t1qAl/RWq'); --my$Password.12

INSERT INTO customers (customer_code, firstname, lastname, street, city, postal_code, email, birthdate, password)
VALUES ('04e12799-a4d1-47e3-84cc-70715130a471', 'Martin', 'Harris', 'Sunshine street', 'New York', 211, 'martin@mail.com', '1977-04-21',
    '$2a$12$UvfDjzA.8xZ49TPrPUUDQeTnfzd2SAc0LG9r0ZNc0w5Vp.2XYpUWu'); --cat.12SWord$

---- Accounts
INSERT INTO bank_accounts (account_code, account_number, consolidated_balance, interval_balance, alias, default_currency, customer_code, start_interval, end_interval)
VALUES ('bf6ce62d-18a3-48c1-9766-044741b0b8d5', '4321 3242 3121 8321', 10.00, 0.00, 'Savings account', 'EUR', '312ace91-3882-4751-9f4b-9c79dd925df2', CURRENT_DATE(), DATEADD(month, 6, CURRENT_DATE()));

INSERT INTO bank_accounts (account_code, account_number, consolidated_balance, interval_balance, alias, default_currency, customer_code, start_interval, end_interval)
VALUES ('72b4849d-2aa8-430d-a4f7-4ad70be81615', '5311 3412 1821 4311', 0.00, 0.00, 'Main account', 'EUR', '04e12799-a4d1-47e3-84cc-70715130a471', CURRENT_DATE(), DATEADD(month, 6, CURRENT_DATE()));

INSERT INTO bank_accounts (account_code, account_number, consolidated_balance, interval_balance, alias, default_currency, customer_code, start_interval, end_interval)
VALUES ('94c14699-b653-4516-9dc1-df7d013e5101', '5351 1232 1824 4511', 10.00, 0.00, 'Movies account', 'EUR', '04e12799-a4d1-47e3-84cc-70715130a471','2022-07-01', '2023-01-01');

---- Transactions
INSERT INTO transactions (transaction_code, discriminator_account_code, origin_account_code, target_account_code, amount, concept, transaction_date)
VALUES ('4b457a5e-ce8e-4d54-8930-a45f8853b928', '94c14699-b653-4516-9dc1-df7d013e5101', '94c14699-b653-4516-9dc1-df7d013e5101', '94c14699-b653-4516-9dc1-df7d013e5101', 10.00, 'Tx1', '2022-08-20');

INSERT INTO transactions (transaction_code, discriminator_account_code, origin_account_code, target_account_code, amount, concept, transaction_date)
VALUES ('8de7e2ea-5eea-416f-bca4-79cfabfe8aed', '94c14699-b653-4516-9dc1-df7d013e5101', '94c14699-b653-4516-9dc1-df7d013e5101', '94c14699-b653-4516-9dc1-df7d013e5101', 5.00, 'Tx2', '2022-08-21');

INSERT INTO transactions (transaction_code, discriminator_account_code, origin_account_code, target_account_code, amount, concept, transaction_date)
VALUES ('1d3bab75-d28a-490e-9c09-d75bc91bb736', '94c14699-b653-4516-9dc1-df7d013e5101', '94c14699-b653-4516-9dc1-df7d013e5101', '94c14699-b653-4516-9dc1-df7d013e5101', 5.00, 'Tx3', '2022-10-21');