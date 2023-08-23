create table category
(
    id        bigint unsigned auto_increment
        primary key,
    parent_id bigint unsigned null,
    name      varchar(20)     not null,
    level     int unsigned    not null,
    constraint FK_category_TO_category_1
        foreign key (parent_id) references category (id)
);

create table member
(
    id         bigint unsigned auto_increment
        primary key,
    email      varchar(30)                               not null,
    password   varchar(255)                              not null,
    name       varchar(20)                               not null,
    money      bigint unsigned default '0'               not null,
    created_at datetime        default CURRENT_TIMESTAMP not null,
    updated_at datetime        default CURRENT_TIMESTAMP not null,
    constraint email
        unique (email)
);

create table address
(
    id          bigint unsigned auto_increment
        primary key,
    member_id   bigint unsigned                      not null,
    is_default  tinyint(1) default 0                 not null,
    road_name   varchar(255)                         not null,
    addr_detail varchar(255)                         not null,
    zip_code    varchar(10)                          not null,
    created_at  datetime   default CURRENT_TIMESTAMP not null,
    updated_at  datetime   default CURRENT_TIMESTAMP not null,
    constraint FK_member_TO_address_1
        foreign key (member_id) references member (id)
);

create table coupon
(
    id              bigint unsigned auto_increment
        primary key,
    member_id       bigint unsigned                       not null,
    name            varchar(20)                           not null,
    discount_policy varchar(20)                           not null,
    discount_value  int unsigned                          not null,
    status          varchar(20) default 'YET'             not null,
    created_at      datetime    default CURRENT_TIMESTAMP not null,
    updated_at      datetime    default CURRENT_TIMESTAMP not null,
    constraint FK_member_TO_coupon_1
        foreign key (member_id) references member (id)
);

create table encryption
(
    member_id bigint unsigned not null
        primary key,
    salt      varchar(255)    not null,
    email     varchar(30)     not null,
    constraint email_UNIQUE
        unique (email),
    constraint FK_member_TO_encryption_1
        foreign key (member_id) references member (id)
);

create table orders
(
    id         bigint unsigned auto_increment
        primary key,
    member_id  bigint unsigned                       not null,
    coupon_id  bigint unsigned                       null,
    status     varchar(10) default 'PENDING'         not null,
    created_at datetime    default CURRENT_TIMESTAMP not null,
    updated_at datetime    default CURRENT_TIMESTAMP not null,
    constraint FK_member_TO_orders_1
        foreign key (member_id) references member (id),
    constraint FK_coupon_TO_orders_1
        foreign key (coupon_id) references coupon (id)
);

create table delivery
(
    order_id    bigint unsigned                not null
        primary key,
    road_name   varchar(255)                   not null,
    addr_detail varchar(255)                   not null,
    zip_code    varchar(255)                   not null,
    status      varchar(255) default 'PENDING' not null,
    created_at  datetime     default (now())   not null,
    updated_at  datetime     default (now())   not null,
    constraint FK_orders_TO_delivery_1
        foreign key (order_id) references orders (id)
);

create table payments
(
    id            bigint unsigned auto_increment
        primary key,
    order_id      bigint unsigned                       not null,
    actual_amount bigint unsigned                       not null,
    type          varchar(20) default 'CASH'            not null,
    created_at    datetime    default CURRENT_TIMESTAMP not null,
    updated_at    datetime    default CURRENT_TIMESTAMP not null,
    constraint FK_orders_TO_payments_1
        foreign key (order_id) references orders (id)
);

create table product
(
    id          bigint unsigned auto_increment
        primary key,
    category_id bigint unsigned                    not null,
    name        varchar(20)                        not null,
    description varchar(255)                       null,
    price       bigint unsigned                    not null,
    quantity    bigint unsigned                    not null,
    code        varchar(255)                       not null,
    created_at  datetime default CURRENT_TIMESTAMP not null,
    updated_at  datetime default CURRENT_TIMESTAMP not null,
    constraint code
        unique (code),
    constraint FK_category_TO_product_1
        foreign key (category_id) references category (id)
);

create table cart
(
    member_id        bigint unsigned not null,
    product_id       bigint unsigned not null,
    product_quantity int unsigned    not null,
    primary key (member_id, product_id),
    constraint FK_member_TO_cart_1
        foreign key (member_id) references member (id),
    constraint FK_product_TO_cart_1
        foreign key (product_id) references product (id)
);

create table likes
(
    member_id  bigint unsigned not null,
    product_id bigint unsigned not null,
    primary key (member_id, product_id),
    constraint FK_member_TO_likes_1
        foreign key (member_id) references member (id),
    constraint FK_product_TO_likes_1
        foreign key (product_id) references product (id)
);

create table product_image
(
    id           bigint unsigned auto_increment
        primary key,
    product_id   bigint unsigned                      not null,
    url          varchar(255)                         not null,
    is_thumbnail tinyint(1) default 0                 not null,
    created_at   datetime   default CURRENT_TIMESTAMP not null,
    updated_at   datetime   default CURRENT_TIMESTAMP not null,
    constraint FK_product_TO_product_image_1
        foreign key (product_id) references product (id)
);

create table product_order
(
    id         bigint unsigned auto_increment
        primary key,
    product_id bigint unsigned                    not null,
    order_id   bigint unsigned                    not null,
    price      bigint unsigned                    not null,
    quantity   int unsigned                       not null,
    created_at datetime default CURRENT_TIMESTAMP not null,
    updated_at datetime default CURRENT_TIMESTAMP not null,
    constraint FK_orders_TO_product_order_1
        foreign key (order_id) references orders (id),
    constraint FK_product_TO_product_order_1
        foreign key (product_id) references product (id)
);