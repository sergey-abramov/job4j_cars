alter table history_owner add column startAt timestamp;
alter table history_owner add column endAt timestamp;
drop table history;