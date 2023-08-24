insert into member (email, password, name, money)
values ('test@naver.com', 'test123', 'test', 2000000);

insert into address (member_id, is_default, road_name, addr_detail, zip_code)
values (1, true, '경기도 안양시 동안구 관악대로 135', '삼성래미안 000동 000호', '13922'),
       (1, false, '경기도 안양시 동안구 관악대로 133', '삼성래미안 111동 111호', '13922');

insert into coupon (member_id, name, discount_policy, discount_value, status)
values (1, '5000원 할인 쿠폰', 'CASH', 5000, 'UNUSED'),
       (1, '10000원 할인 쿠폰', 'CASH', 10000, 'USED'),
       (1, '10% 할인 쿠폰', 'DISCOUNT', 10, 'UNUSED'),
       (1, '20% 할인 쿠폰', 'DISCOUNT', 20, 'EXPIRED');


insert into likes (member_id, product_id)
values (1, 2);

