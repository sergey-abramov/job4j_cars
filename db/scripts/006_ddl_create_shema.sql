create table engine(
    id serial primary key,
    name varchar
);

create table car(
    id serial primary key,
    owner_id int not null unique references owners(id)
    engine_id int not null unique references engine(id)
);

create table owners(
    id serial primary key,
    name varchar
);

create table history_owner(
    id serial primary key,
    owner_id int not null references owners(id),
    car_id int not null references car(id)
);

create table history(
    id serial primary key,
    startAt timestamp,
    endAt timestamp
);

alter table auto_post add column car_id int references car(id) not null;