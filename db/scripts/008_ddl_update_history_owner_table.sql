alter table history_owner add column startAt timestamp;
alter table history_owner add column endAt timestamp;
drop table history;
alter table car add column title varchar;