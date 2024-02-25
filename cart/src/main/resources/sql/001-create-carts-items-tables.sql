DROP TABLE IF EXISTS carts CASCADE;
CREATE TABLE carts (
    id        SERIAl       PRIMARY KEY,
    user_id   VARCHAR(36)  NOT NULL UNIQUE,
    reduction DECIMAL(5,2)
);

DROP TABLE IF EXISTS items;
CREATE TABLE items (
    id         SERIAL        PRIMARY KEY,
    cart_id    INT           NOT NULL,
    product_id INT           NOT NULL,
    name       VARCHAR(255)  NOT NULL,
    quantity   INT           NOT NULL,
    price      DECIMAL(14,2) NOT NULL,
    reduction  DECIMAL(5,2),
    CONSTRAINT fk_cart_id_table_items FOREIGN KEY (cart_id) REFERENCES carts (id)
);