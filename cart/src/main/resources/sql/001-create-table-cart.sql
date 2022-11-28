CREATE TABLE IF NOT EXISTS cart_item
(
    id           INT(11) AUTO_INCREMENT PRIMARY KEY,
    product_id   INT(11)      NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    quantity     INT(11)      NOT NULL,
    price        FLOAT  NOT NULL,
    price_to_pay FLOAT    NOT NULL
);

CREATE TABLE IF NOT EXISTS cart_cart_items
(
    cart_id       INT(11) AUTO_INCREMENT PRIMARY KEY,
    cart_items_id INT(11),
    CONSTRAINT fk_cart_id FOREIGN KEY (cart_items_id) REFERENCES cart_item (id),
    CONSTRAINT fk_cart_item_id FOREIGN KEY (cart_id) REFERENCES cart (id)
);

CREATE TABLE IF NOT EXISTS cart
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY
);



