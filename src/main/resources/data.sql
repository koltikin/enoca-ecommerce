
INSERT INTO customers (created_date_time, last_update_date_time, is_deleted, first_name, last_name, email,pass_word)
VALUES
    ('2024-06-15 10:00:00', '2024-06-15 10:00:00', false, 'John', 'Doe', 'john.doe@example.com','Abc123'),
    ('2024-06-15 10:00:00', '2024-06-15 10:00:00', false, 'Jane', 'Smith', 'jane.smith@example.com','Abc123'),
    ('2024-06-15 10:00:00', '2024-06-15 10:00:00', false, 'Michael', 'Johnson', 'michael.johnson@example.com','Abc123');


INSERT INTO products (created_date_time, last_update_date_time, is_deleted, product_name, price, in_stock_quantity)
VALUES
    ('2024-06-15 10:00:00', '2024-06-15 10:00:00', false, 'Laptop', 999.99, 10),
    ('2024-06-15 10:00:00', '2024-06-15 10:00:00', false, 'Smartphone', 599.50, 20),
    ('2024-06-15 10:00:00', '2024-06-15 10:00:00', false, 'Headphones', 149.99, 30),
    ('2024-06-15 10:00:00', '2024-06-15 10:00:00', false, 'Tablet', 299.95, 15),
    ('2024-06-15 10:00:00', '2024-06-15 10:00:00', false, 'Fitness Tracker', 79.99, 25);

INSERT INTO carts (created_date_time, last_update_date_time, is_deleted, total_price,customer_id)
VALUES
    ('2024-06-15 10:00:00', '2024-06-15 10:00:00', false, 0.00,1),
    ('2024-06-15 10:00:00', '2024-06-15 10:00:00', false, 0.00,2),
    ('2024-06-15 10:00:00', '2024-06-15 10:00:00', false, 0.00,3);
