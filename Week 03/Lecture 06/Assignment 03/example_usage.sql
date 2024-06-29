-- EXAMPLE USAGE OF QUERY ASSIGNMENT 3

-- Create view to show list products customer bought: customer_id, customer_name, product_id, product_name, quantity, amount, created_date​
SELECT * FROM customer_product_purchases;

-- Create a function calculating revenue by cashier: input cashier_id
SELECT calculate_revenue_by_cashier(1);

-- Create table revenue_report: id, year, month, day, amount​
SELECT * FROM revenue_report;

-- revenue of day: input day of year​
CALL calculate_revenue_of_day('2024-06-05');

-- revenue of month: input month of year​
CALL calculate_revenue_of_month(2024, 6);

-- revenue of year: input year​
CALL calculate_revenue_of_year(2024);