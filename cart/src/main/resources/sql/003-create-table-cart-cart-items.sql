CREATE TABLE IF NOT EXISTS cart_cart_items
(
    cart_id       INT(11) AUTO_INCREMENT PRIMARY KEY,
    cart_items_id INT(11),
    CONSTRAINT fk_cart_id FOREIGN KEY (cart_items_id) REFERENCES cart_item (id),
    CONSTRAINT fk_cart_item_id FOREIGN KEY (cart_id) REFERENCES cart (id)
    );