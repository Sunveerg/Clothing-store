USE `product-db`;

create table if not exists products (
    id SERIAL,
    product_id VARCHAR(36) UNIQUE,
    product_name VARCHAR(50),
    category VARCHAR(50),
    brand VARCHAR(255),
    price DECIMAL(19,2),
    stock INTEGER,
    product_description VARCHAR(100),
    PRIMARY KEY (id)
);