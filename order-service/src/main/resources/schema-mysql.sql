USE `order-db`;

create table if not exists `orders` (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_id VARCHAR(36) UNIQUE,
    order_date VARCHAR(50),
    status VARCHAR(50),
    department_id VARCHAR(50),
    employee_id VARCHAR(50),
    product_id VARCHAR(50),
    customer_id VARCHAR(50),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    commission_rate DOUBLE,
    name VARCHAR(50),
    headCount INTEGER,
    product_name VARCHAR(50),
    product_stock INTEGER
);