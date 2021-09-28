truncate customer cascade;
truncate product;

insert into customer (id, title, is_deleted, created_at) values
('475d9655-4630-4a8e-8b15-1f2d6849785b', 'title 1', false, now()),
('18bbfac6-3d2a-43ca-a6b2-265d315017a3', 'title 2', true, now()),
('988025e7-32c8-4a9c-a578-ff09681c8931', 'title 3', false, now());

insert into product (id, customer_id, title, description, price, is_deleted, created_at) values
('cd8e82bf-2a8d-48c8-8d60-ace3a87a4a6b', '475d9655-4630-4a8e-8b15-1f2d6849785b', 'product 1', 'description of product 1', 0.1, false, now()),
('007d9c55-c992-4d13-8a05-8947b2ba79cd', '475d9655-4630-4a8e-8b15-1f2d6849785b', 'product 2', 'description of product 2', 10.1, false, now()),
('725e6f60-e0ed-4274-8409-aa6119c64b01', '475d9655-4630-4a8e-8b15-1f2d6849785b', 'product 3', 'description of product 3', 50.1, false, now()),
('0c3645c6-4fca-4431-b3c2-3699a6eee2f2', '475d9655-4630-4a8e-8b15-1f2d6849785b', 'product 4', 'description of product 4', 55.1, true, now()),
('f520fccb-893e-4ccd-b99d-cddbd4409a9b', '18bbfac6-3d2a-43ca-a6b2-265d315017a3', 'product 5', 'description of product 5', 666.66, false, now()),
('678d4aea-1f73-11ec-9621-0242ac130002', '988025e7-32c8-4a9c-a578-ff09681c8931', 'product 6', 'description of product 6', 0.66, false, now());

