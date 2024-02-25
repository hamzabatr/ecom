DROP TABLE IF EXISTS products;
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    illustration VARCHAR(255),
    price FLOAT NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    likes INT,
    dislikes INT
);

DROP TABLE IF EXISTS comments;
CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    comment TEXT,
    note INT NOT NULL,
    likes INT,
    dislikes INT,
    product_id INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products (id)
);