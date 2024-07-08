-- Insert data into customer
INSERT INTO customer (name, phone) VALUES 
    ('John Doe', '123-456-7890'),
    ('Jane Smith', '987-654-3210'),
    ('Michael Johnson', '111-222-3333'),
    ('Emily Davis', '123-123-1230'),
    ('Chris Brown', '321-654-7890');

-- Insert data into cashier
INSERT INTO cashier (name) VALUES 
    ('Alice'),
    ('Bob');

-- Insert data into product
INSERT INTO product (name, price) VALUES 
    ('Product A', 10.00),
    ('Product B', 15.00),
    ('Product C', 20.00);

-- Insert data into invoice
INSERT INTO invoice (customer_id, cashier_id, amount, created_date) VALUES 
    (1, 1, 40.00, '2024-04-01'),
    (2, 1, 60.00, '2024-04-02'),
    (3, 2, 65.00, '2024-05-03'),
    (4, 2, 80.00, '2024-06-04'),
    (5, 1, 90.00, '2024-06-05'),
    (5, 1, 85.00, '2024-06-05');

-- Insert data into invoice_detail
INSERT INTO invoice_detail (quantity, product_id, product_price, invoice_id, amount) VALUES 
    (1, 1, 10.00, 1, 10.00),
    (2, 2, 15.00, 1, 30.00),
    (3, 3, 20.00, 2, 60.00),
    (2, 1, 10.00, 3, 20.00),
    (3, 2, 15.00, 3, 45.00),
    (4, 3, 20.00, 4, 80.00),
    (1, 1, 10.00, 5, 10.00),
    (4, 2, 15.00, 5, 60.00),
    (1, 3, 20.00, 5, 20.00),
    (2, 3, 20.00, 6, 40.00),
    (3, 2, 15.00, 6, 45.00);