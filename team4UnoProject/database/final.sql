drop table scoreboard;
drop table users; 

create table users(
username varchar(30),
password varbinary(24));

create table scoreboard(
username	varchar(30),
score	NUMERIC(2));


alter table users
	add constraint users_username_pk primary key(username);

alter table scoreboard
	add constraint scoreboard_username_pk primary key(username);
	
alter table scoreboard
	add constraint scoreboard_username_fk foreign key(username)
	references users(username);
	
insert into users
	values('jsmith@uca.edu', aes_encrypt('hello123', 'key'));
insert into users
	values('msmith@uca.edu', aes_encrypt('pass123', 'key'));
insert into users
	values('tjones@yahoo.com', aes_encrypt('123456', 'key'));
insert into users
	values('jjones@yahoo.com', aes_encrypt('hello1234', 'key')); 

