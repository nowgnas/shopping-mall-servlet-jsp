ALTER TABLE encryption
DROP CONSTRAINT PK_encryption;

ALTER TABLE likes
    DROP CONSTRAINT PK_likes;

ALTER TABLE member
    DROP CONSTRAINT PK_member;

ALTER TABLE product
    DROP CONSTRAINT PK_product;

ALTER TABLE cart
    DROP CONSTRAINT PK_cart;

ALTER TABLE orders
    DROP CONSTRAINT PK_orders;

ALTER TABLE product_order
    DROP CONSTRAINT PK_product_order;

ALTER TABLE category
    DROP CONSTRAINT PK_category;

ALTER TABLE coupon
    DROP CONSTRAINT PK_coupon;

ALTER TABLE address
    DROP CONSTRAINT PK_address;

ALTER TABLE product_image
    DROP CONSTRAINT PK_product_image;

ALTER TABLE payments
    DROP CONSTRAINT PK_payments;

ALTER TABLE delivery
    DROP CONSTRAINT PK_delivery;

ALTER TABLE encryption
DROP CONSTRAINT FK_member_TO_encryption_1;

ALTER TABLE likes
    DROP CONSTRAINT FK_member_TO_likes_1;

ALTER TABLE likes
    DROP CONSTRAINT FK_product_TO_likes_1;

ALTER TABLE product
    DROP CONSTRAINT FK_category_TO_product_1;

ALTER TABLE cart
    DROP CONSTRAINT FK_member_TO_cart_1;

ALTER TABLE cart
    DROP CONSTRAINT FK_product_TO_cart_1;

ALTER TABLE orders
    DROP CONSTRAINT FK_member_TO_orders_1;

ALTER TABLE product_order
    DROP CONSTRAINT FK_product_TO_product_order_1;

ALTER TABLE product_order
    DROP CONSTRAINT FK_orders_TO_product_order_1;

ALTER TABLE category
    DROP CONSTRAINT FK_category_TO_category_1;

ALTER TABLE coupon
    DROP CONSTRAINT FK_member_TO_coupon_1;

ALTER TABLE address
    DROP CONSTRAINT FK_member_TO_address_1;

ALTER TABLE product_image
    DROP CONSTRAINT FK_product_TO_product_image_1;

ALTER TABLE payments
    DROP CONSTRAINT FK_orders_TO_payments_1;

ALTER TABLE delivery
    DROP CONSTRAINT FK_orders_TO_delivery_1;

DROP TABLE address;
DROP TABLE cart;
DROP TABLE category;
DROP TABLE coupon;
DROP TABLE delivery;
DROP TABLE likes;
DROP TABLE member;
DROP TABLE orders;
DROP TABLE payments;
DROP TABLE product;
DROP TABLE product_image;
DROP TABLE product_order;
