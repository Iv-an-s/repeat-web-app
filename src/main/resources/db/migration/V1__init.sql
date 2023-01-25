create table products
(
    id bigserial primary key,
    title varchar(255),
    price int);
insert into products (title, price)
values ('Bread', 25),
        ('Milk', 80),
        ('Cheese', 450);