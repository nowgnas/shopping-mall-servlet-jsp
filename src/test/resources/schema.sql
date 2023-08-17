CREATE TABLE IF NOT EXISTS likes
(

    member_id
        BIGINT
            UNSIGNED
        NOT
            NULL,

    product_id
        BIGINT
            UNSIGNED
        NOT
            NULL
);

CREATE TABLE IF NOT EXISTS member
(

    id
               BIGINT
                   UNSIGNED
                               NOT
                                   NULL,

    email
               VARCHAR(20)     NOT NULL UNIQUE,
    password   VARCHAR(20)     NOT NULL,
    name       VARCHAR(20)     NOT NULL,
    money      BIGINT UNSIGNED NOT NULL DEFAULT 0,
    created_at DATETIME        NOT NULL DEFAULT NOW
        (
        ),
    updated_at DATETIME        NOT NULL DEFAULT NOW
        (
        )
);

CREATE TABLE IF NOT EXISTS product
(

    id
                BIGINT
                    UNSIGNED
                                NOT
                                    NULL,

    category_id
                BIGINT
                    UNSIGNED
                                NOT
                                    NULL,

    name
                VARCHAR(20)     NOT NULL,
    description VARCHAR(255)    NULL     DEFAULT NULL,
    price       BIGINT UNSIGNED NOT NULL,
    quantity    BIGINT UNSIGNED NOT NULL,
    code        VARCHAR(255)    NOT NULL UNIQUE,
    created_at  DATETIME        NOT NULL DEFAULT NOW
        (
        ),
    updated_at  DATETIME        NOT NULL DEFAULT NOW
        (
        )
);

CREATE TABLE IF NOT EXISTS cart
(

    member_id
        BIGINT
            UNSIGNED
        NOT
            NULL,

    product_id
        BIGINT
            UNSIGNED
        NOT
            NULL,

    product_quantity
        INT
            UNSIGNED
        NOT
            NULL
);

CREATE TABLE IF NOT EXISTS orders
(

    id
               BIGINT
                   UNSIGNED
                           NOT
                               NULL,

    member_id
               BIGINT
                   UNSIGNED
                           NOT
                               NULL,

    status
               VARCHAR(10) NOT NULL DEFAULT 'PENDING',
    created_at DATETIME    NOT NULL DEFAULT NOW
        (
        ),
    updated_at DATETIME    NOT NULL DEFAULT NOW
        (
        )
);

CREATE TABLE IF NOT EXISTS product_order
(

    id
               BIGINT
                   UNSIGNED
                        NOT
                            NULL,

    product_id
               BIGINT
                   UNSIGNED
                        NOT
                            NULL,

    order_id
               BIGINT
                   UNSIGNED
                        NOT
                            NULL,

    price
               BIGINT
                   UNSIGNED
                        NOT
                            NULL,

    quantity
               INT
                   UNSIGNED
                        NOT
                            NULL,

    created_at
               DATETIME
                        NOT
                            NULL
                                 DEFAULT
                                     NOW
                                         (
                                         ),
    updated_at DATETIME NOT NULL DEFAULT NOW
        (
        )
);

CREATE TABLE IF NOT EXISTS category
(

    id
          BIGINT
              UNSIGNED
                       NOT
                           NULL,

    parent_id
          BIGINT
              UNSIGNED
                       NULL,

    name
          VARCHAR(20)  NOT NULL,
    level INT UNSIGNED NOT NULL
);

CREATE TABLE IF NOT EXISTS coupon
(

    id
                    BIGINT
                        UNSIGNED
                                 NOT
                                     NULL,

    member_id
                    BIGINT
                        UNSIGNED
                                 NOT
                                     NULL,

    name
                    VARCHAR(20)  NOT NULL,
    discount_policy VARCHAR(20)  NOT NULL,
    discount_value  INT UNSIGNED NOT NULL,
    status          VARCHAR(20)  NOT NULL DEFAULT 'YET',
    created_at      DATETIME     NOT NULL DEFAULT NOW
        (
        ),
    updated_at      DATETIME     NOT NULL DEFAULT NOW
        (
        )
);

CREATE TABLE IF NOT EXISTS address
(

    id
                BIGINT
                    UNSIGNED
                             NOT
                                 NULL,

    member_id
                BIGINT
                    UNSIGNED
                             NOT
                                 NULL,

    is_default
                BOOLEAN
                             NOT
                                 NULL
                                      DEFAULT
                                          FALSE,

    road_name
                VARCHAR(255) NOT NULL,
    addr_detail VARCHAR(255) NOT NULL,
    zip_code    VARCHAR(10)  NOT NULL,
    created_at  DATETIME     NOT NULL DEFAULT NOW
        (
        ),
    updated_at  DATETIME     NOT NULL DEFAULT NOW
        (
        )
);

CREATE TABLE IF NOT EXISTS product_image
(

    id
                 BIGINT
                     UNSIGNED
                              NOT
                                  NULL,

    product_id
                 BIGINT
                     UNSIGNED
                              NOT
                                  NULL,

    url
                 VARCHAR(255) NOT NULL,
    is_thumbnail BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at   DATETIME     NOT NULL DEFAULT NOW
        (
        ),
    updated_at   DATETIME     NOT NULL DEFAULT NOW
        (
        )
);

CREATE TABLE IF NOT EXISTS payments
(

    id
               BIGINT
                   UNSIGNED
                           NOT
                               NULL,

    order_id
               BIGINT
                   UNSIGNED
                           NOT
                               NULL,

    actual_amount
               BIGINT
                   UNSIGNED
                           NOT
                               NULL,

    type
               VARCHAR(20) NOT NULL DEFAULT 'CASH',
    created_at DATETIME    NOT NULL DEFAULT NOW
        (
        ),
    updated_at DATETIME    NOT NULL DEFAULT NOW
        (
        )
);

CREATE TABLE IF NOT EXISTS delivery
(

    order_id
                BIGINT
                    UNSIGNED
                             NOT
                                 NULL,

    road_name
                VARCHAR(255) NOT NULL,
    addr_detail VARCHAR(255) NOT NULL,
    zip_code    VARCHAR(255) NOT NULL,
    status      VARCHAR(255) NOT NULL DEFAULT 'PENDING',
    created_at  DATETIME     NOT NULL DEFAULT NOW(),
    updated_at  DATETIME     NOT NULL DEFAULT NOW()
);

ALTER TABLE likes
    ADD CONSTRAINT PK_likes PRIMARY KEY (
                                         member_id,
                                         product_id
        );

ALTER TABLE member
    ADD CONSTRAINT PK_member PRIMARY KEY (
                                          id
        );
ALTER TABLE member
    MODIFY id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE product
    ADD CONSTRAINT PK_product PRIMARY KEY (
                                           id
        );
ALTER TABLE product
    MODIFY id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE cart
    ADD CONSTRAINT PK_cart PRIMARY KEY (
                                        member_id,
                                        product_id
        );

ALTER TABLE orders
    ADD CONSTRAINT PK_orders PRIMARY KEY (
                                          id
        );
ALTER TABLE orders
    MODIFY id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE product_order
    ADD CONSTRAINT PK_product_order PRIMARY KEY (
                                                 id
        );
ALTER TABLE product_order
    MODIFY id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE category
    ADD CONSTRAINT PK_category PRIMARY KEY (
                                            id
        );
ALTER TABLE category
    MODIFY id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE coupon
    ADD CONSTRAINT PK_coupon PRIMARY KEY (
                                          id
        );
ALTER TABLE coupon
    MODIFY id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE address
    ADD CONSTRAINT PK_address PRIMARY KEY (
                                           id
        );
ALTER TABLE address
    MODIFY id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE product_image
    ADD CONSTRAINT PK_product_image PRIMARY KEY (
                                                 id
        );
ALTER TABLE product_image
    MODIFY id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE payments
    ADD CONSTRAINT PK_payments PRIMARY KEY (
                                            id
        );
ALTER TABLE payments
    MODIFY id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT;

ALTER TABLE delivery
    ADD CONSTRAINT PK_delivery PRIMARY KEY (order_id);

ALTER TABLE likes
    ADD CONSTRAINT FK_member_TO_likes_1 FOREIGN KEY (member_id)
        REFERENCES member (id);

ALTER TABLE likes
    ADD CONSTRAINT FK_product_TO_likes_1 FOREIGN KEY (
                                                      product_id
        )
        REFERENCES product (
                            id
            );

ALTER TABLE product
    ADD CONSTRAINT FK_category_TO_product_1 FOREIGN KEY (
                                                         category_id
        )
        REFERENCES category (
                             id
            );

ALTER TABLE cart
    ADD CONSTRAINT FK_member_TO_cart_1 FOREIGN KEY (
                                                    member_id
        )
        REFERENCES member (
                           id
            );

ALTER TABLE cart
    ADD CONSTRAINT FK_product_TO_cart_1 FOREIGN KEY (
                                                     product_id
        )
        REFERENCES product (
                            id
            );

ALTER TABLE orders
    ADD CONSTRAINT FK_member_TO_orders_1 FOREIGN KEY (
                                                      member_id
        )
        REFERENCES member (
                           id
            );

ALTER TABLE product_order
    ADD CONSTRAINT FK_product_TO_product_order_1 FOREIGN KEY (
                                                              product_id
        )
        REFERENCES product (
                            id
            );

ALTER TABLE product_order
    ADD CONSTRAINT FK_orders_TO_product_order_1 FOREIGN KEY (
                                                             order_id
        )
        REFERENCES orders (
                           id
            );

ALTER TABLE category
    ADD CONSTRAINT FK_category_TO_category_1 FOREIGN KEY (
                                                          parent_id
        )
        REFERENCES category (
                             id
            );

ALTER TABLE coupon
    ADD CONSTRAINT FK_member_TO_coupon_1 FOREIGN KEY (
                                                      member_id
        )
        REFERENCES member (
                           id
            );

ALTER TABLE address
    ADD CONSTRAINT FK_member_TO_address_1 FOREIGN KEY (
                                                       member_id
        )
        REFERENCES member (
                           id
            );

ALTER TABLE product_image
    ADD CONSTRAINT FK_product_TO_product_image_1 FOREIGN KEY (
                                                              product_id
        )
        REFERENCES product (
                            id
            );

ALTER TABLE payments
    ADD CONSTRAINT FK_orders_TO_payments_1 FOREIGN KEY (
                                                        order_id
        )
        REFERENCES orders (id);

ALTER TABLE delivery
    ADD CONSTRAINT FK_orders_TO_delivery_1 FOREIGN KEY (
                                                        order_id
        )
        REFERENCES orders (
                           id
            );
