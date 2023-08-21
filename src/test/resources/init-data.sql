insert into member (email, password, name, money)
values ('test@naver.com', 'test123', 'test', 2000000);

insert into address (member_id, is_default, road_name, addr_detail, zip_code)
values (1, true, '경기도 안양시 동안구 관악대로 135', '삼성래미안 000동 000호', '13922'),
       (1, false, '경기도 안양시 동안구 관악대로 133', '삼성래미안 111동 111호', '13922');

insert into coupon (member_id, name, discount_policy, discount_value, status)
values (1, '5000원 할인 쿠폰', 'CASH', 5000, 'UNUNSED'),
       (1, '10000원 할인 쿠폰', 'CASH', 10000, 'USED'),
       (1, '10% 할인 쿠폰', 'DISCOUNT', 10, 'UNUNSED'),
       (1, '20% 할인 쿠폰', 'DISCOUNT', 20, 'EXPIRED');

insert into category (parent_id, name, level)
values (null, '컴퓨터', 1),
       (1, '노트북', 2),
       (1, '데스크탑', 2),
       (2, '맥 북', 3),
       (2, '갤럭시 북', 3),
       (3, '아이맥', 3),
       (3, '갤럭시 데스크탑', 3);

insert into category (parent_id, name, level)
values (null, '가전', 1),
       (8, '생활 가전', 2),
       (8, '주방 가전', 2),
       (9, '세탁기', 3),
       (9, '청소기', 3),
       (10, '냉장고', 3),
       (10, '정수기', 3);

insert into product (category_id, name, description, price, quantity, code)
values (4, '맥북 프로 16', '맥북 프로 16 상세정보', 3000000, 2, 'PRODUCT-001'),
       (5, '갤럭시북 20', '갤럭시북 20 상세정보', 2000000, 10, 'PRODUCT-002'),
       (6, '아이맥 20', '아이맥 20 상세정보', 1500000, 5, 'PRODUCT-003'),
       (7, '갤럭시 데스크탑 20', '갤럭시 데스크탑 20 상세정보', 1400000, 7, 'PRODUCT-004'),
       (11, 'LG 세탁기', 'LG 세탁기 상세정보', 1000000, 50, 'PRODUCT-005'),
       (12, 'LG 로봇 청소기', 'LG 로봇 청소기 상세정보', 600000, 100, 'PRODUCT-006'),
       (13, '삼성 냉장고', '삼성 냉장고 상세정보', 1800000, 80, 'PRODUCT-007'),
       (14, '웅진 코웨이 정수기', '웅진 코웨이 정수기 상세정보', 500000, 90, 'PRODUCT-008');

