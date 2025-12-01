<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS menu_categories (
       id SERIAL PRIMARY KEY,
       restaurant_id BIGINT NOT NULL,
       name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS menu_items (
      id SERIAL PRIMARY KEY,
      category_id BIGINT NOT NULL,
      name VARCHAR(100) NOT NULL,
      description TEXT,
      price NUMERIC(10,2) NOT NULL,
      image_url TEXT,
      FOREIGN KEY (category_id) REFERENCES menu_categories(id) ON DELETE CASCADE
);
=======
CREATE TABLE IF NOT EXISTS plates(
    id SERIAL PRIMARY KEY,
    store_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    image VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS drinks(
    id SERIAL PRIMARY KEY,
    store_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    image VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS desserts(
    id SERIAL PRIMARY KEY,
    store_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    image VARCHAR(255) NOT NULL
);
>>>>>>> 4e396fcfa0f4cf2ce7a63529b590db803433669e
