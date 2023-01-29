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
        ('Cheese1', 450, 1),
        ('Cheese2', 550, 1),
        ('Cheese3', 650, 1),
        ('Cheese4', 450, 1),
        ('Cheese5', 550, 1),
        ('Cheese6', 650, 1),
        ('Cheese7', 750, 1),
        ('Cheese8', 850, 1),
        ('Cheese9', 950, 1),
        ('Cheese10', 1050, 1),
        ('Cheese11', 1150, 1),
        ('Cheese12', 1250, 1),
        ('Cheese13', 1350, 1),
        ('Cheese14', 1450, 1),
        ('Cheese15', 1550, 1),
        ('Cheese16', 1650, 1),
        ('Cheese17', 1750, 1),
        ('Cheese18', 1650, 1),
        ('Cheese19', 1750, 1),
        ('Cheese20', 1850, 1);

