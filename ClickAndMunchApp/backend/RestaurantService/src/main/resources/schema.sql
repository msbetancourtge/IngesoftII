CREATE TABLE IF NOT EXISTS stores (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    alias VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    latitude DECIMAL(11,8) NOT NULL,
    longitude decimal(11,8) NOT NULL
    );

CREATE TABLE IF NOT EXISTS orders(
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    store_id INT NOT NULL REFERENCES stores(id),
    total_price DECIMAL(10,2) NOT NULL,
    status VARCHAR(255) NOT NULL,
    payment_method VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS plates(
    id SERIAL PRIMARY KEY,
    store_id INT NOT NULL REFERENCES stores(id),
    stores_key INT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    image VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS drinks(
    id SERIAL PRIMARY KEY,
    store_id INT NOT NULL REFERENCES stores(id),
    stores_key INT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    image VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS desserts(
    id SERIAL PRIMARY KEY,
    store_id INT NOT NULL REFERENCES stores(id),
    stores_key INT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    image VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS order_plates(
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL REFERENCES orders(id),
    plate_id INT NOT NULL REFERENCES plates(id)
    );

CREATE TABLE IF NOT EXISTS order_drinks(
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL REFERENCES orders(id),
    drink_id INT NOT NULL REFERENCES drinks(id)
    );

CREATE TABLE IF NOT EXISTS order_desserts(
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL REFERENCES orders(id),
    dessert_id INT NOT NULL REFERENCES desserts(id)
    );

CREATE TABLE IF NOT EXISTS chefs(
    id       SERIAL PRIMARY KEY,
    store_id INT NOT NULL REFERENCES stores (id)

);