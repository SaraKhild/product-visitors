drop TABLE if EXISTS product; 
create table product (
    id serial  PRIMARY KEY,
    title VARCHAR(500),
    price INTEGER 
);
