truncate customer cascade;
truncate product;

insert into customer (title, is_deleted, created_at) values
('title 1', false, now()),
('title 2', true, now()),
('title 3', false, now());

insert into product (customer_id, title, description, price, is_deleted, created_at)
select id, 'product 1', 'description of product 1', 0.1, false, now() from customer where title = 'title 1';
insert into product (customer_id, title, description, price, is_deleted, created_at)
select id, 'product 2', 'description of product 2', 10.1, false, now() from customer where title = 'title 1';
insert into product (customer_id, title, description, price, is_deleted, created_at)
select id, 'product 3', 'description of product 3', 50.1, false, now() from customer where title = 'title 1';
insert into product (customer_id, title, description, price, is_deleted, created_at)
select id, 'product 4', 'description of product 4', 55.1, true, now() from customer where title = 'title 1';
insert into product (customer_id, title, description, price, is_deleted, created_at)
select id, 'product 5', 'description of product 5', 666.66, false, now() from customer where title = 'title 2';