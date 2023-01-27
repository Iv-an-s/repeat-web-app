create table categories
(id bigserial primary key, title varchar(255));
--   В postgreSQL нет типа данных bigserial - это bigint + sequence генератор.

insert into categories(title)
values ('food');

create table products
(
    id bigserial primary key,
    title varchar(255),
    price int,
    category_id bigint references categories (id)
);
insert into products (title, price, category_id)
values ('Bread', 25, 1),
        ('Milk', 80, 1),
        ('Cheese', 450, 1),
        ('Cheese', 550, 1),
        ('Cheese', 650, 1),
        ('Cheese', 750, 1),
        ('Cheese', 850, 1),
        ('Cheese', 950, 1),
        ('Cheese', 1050, 1),
        ('Cheese', 1150, 1),
        ('Cheese', 1250, 1),
        ('Cheese', 1350, 1),
        ('Cheese', 1450, 1),
        ('Cheese', 1450, 1),
        ('Cheese', 1450, 1),
        ('Cheese', 1450, 1),
        ('Cheese', 1550, 1);
