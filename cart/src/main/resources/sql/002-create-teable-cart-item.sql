CREATE TABLE IF NOT EXISTS cart_item
(
    id           INT(11) AUTO_INCREMENT PRIMARY KEY,
    product_id   INT(11)      NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    quantity     INT(11)      NOT NULL,
    price        FLOAT  NOT NULL,
    price_to_pay FLOAT    NOT NULL
    );