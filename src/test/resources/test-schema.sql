create table if not exists customer (
    id uuid default gen_random_uuid() primary key,
    title varchar(255) not null,
    is_deleted boolean default false not null,
    created_at timestamp not null,
    modified_at timestamp
);

create table if not exists product (
    id uuid default gen_random_uuid() primary key,
    customer_id uuid not null,
    title varchar(255) not null,
    description varchar(1024),
    price decimal(10, 2) not null,
    is_deleted boolean default false not null,
    created_at timestamp not null,
    modified_at timestamp,
    constraint "fk_customer_id_id" foreign key (customer_id) references customer(id) on delete cascade
);

create index if not exists fk_customer_id_id on product(customer_id);