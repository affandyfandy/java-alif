-- Create view to show list products customer bought: customer_id, customer_name, product_id, product_name, quantity, amount, created_date​
CREATE VIEW customer_product_purchases AS
SELECT 
    c.id AS customer_id, 
    c.name AS customer_name, 
    p.id AS product_id, 
    p.name AS product_name, 
    id.quantity, 
    id.amount, 
    i.created_date
FROM 
    customer c
JOIN 
    invoice i ON c.id = i.customer_id
JOIN 
    invoice_detail id ON i.id = id.invoice_id
JOIN 
    product p ON id.product_id = p.id;

-- Create a function calculating revenue by cashier: input cashier_id
DELIMITER //
CREATE FUNCTION calculate_revenue_by_cashier(cashier_id INT)
RETURNS DECIMAL(10, 2)
BEGIN
    DECLARE total_revenue DECIMAL(10, 2);
    SELECT 
        SUM(i.amount) INTO total_revenue
    FROM 
        invoice i
    WHERE 
        i.cashier_id = cashier_id;
    RETURN total_revenue;
END //
DELIMITER ;

-- Create table revenue_report: id, year, month, day, amount​
CREATE TABLE revenue_report (
    id INT AUTO_INCREMENT PRIMARY KEY,
    year INT,
    month INT,
    day INT,
    amount DECIMAL(10, 2) NOT NULL
);

-- revenue of day: input day of year​
DELIMITER //
CREATE PROCEDURE calculate_revenue_of_day(IN input_day DATE)
BEGIN
    DECLARE total_revenue DECIMAL(10,2);
    SELECT 
        SUM(i.amount) INTO total_revenue
    FROM 
        invoice i
    WHERE 
        DATE(i.created_date) = input_day;
    INSERT INTO revenue_report (year, month, day, amount) VALUES 
        (YEAR(input_day), MONTH(input_day), DAY(input_day), total_revenue);
END //
DELIMITER ;

-- revenue of month: input month of year​
DELIMITER //
CREATE PROCEDURE calculate_revenue_of_month(IN input_year INT, IN input_month INT)
BEGIN
    DECLARE total_revenue DECIMAL(10,2);
    SELECT 
        SUM(i.amount) INTO total_revenue
    FROM 
        invoice i
    WHERE 
        YEAR(i.created_date) = input_year AND MONTH(i.created_date) = input_month;
    INSERT INTO revenue_report (year, month, day, amount) VALUES 
        (input_year, input_month, NULL, total_revenue);
END //
DELIMITER ;


-- revenue of year: input year​
DELIMITER //
CREATE PROCEDURE calculate_revenue_of_year(year_inp INT)
BEGIN
    DECLARE total_revenue DECIMAL(10, 2);
    SELECT 
        SUM(i.amount) INTO total_revenue
    FROM 
        invoice i
    WHERE 
        YEAR(i.created_date) = year_inp;
    INSERT INTO revenue_report (year, month, day, amount) VALUES 
        (year_inp, NULL, NULL, total_revenue);
END //
DELIMITER ;