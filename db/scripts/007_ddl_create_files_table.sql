create table files
(
    id   serial primary key,
    name varchar not null,
    path varchar not null unique,
    post_id int not null unique references auto_post(id)
);