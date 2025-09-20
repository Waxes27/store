CREATE TABLE store_order_products
(
    orders_id   BIGINT NOT NULL,
    products_id BIGINT NOT NULL,
    CONSTRAINT pk_store_order_products PRIMARY KEY (orders_id, products_id)
);

ALTER TABLE store_order_products
    ADD CONSTRAINT fk_stoordpro_on_order FOREIGN KEY (orders_id) REFERENCES store_order (id);

ALTER TABLE store_order_products
    ADD CONSTRAINT fk_stoordpro_on_product FOREIGN KEY (products_id) REFERENCES product (id);

