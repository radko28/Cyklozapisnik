#team edition
#team database
#address,chain,cyklo,  team,  tables
use team;

drop table if exists address;
drop table if exists chain;
drop table if exists cyklo;
drop table if exists team;


create table address (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        email varchar(32),
	password varchar(16),
	firstname varchar(32),
	surname varchar(32),
	mobil char(14),
	address char(64),
	city char(64),
	zip char(10),
	pic varchar(64),
	web varchar(64),
	log enum('0','1') default '0',
	primary key(id)
);

create table chain (
	id_user int,
	id_team int
);

create table cyklo(
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        id_user int,
	date date,
	time time,
	veloce float(4,2),
	distance float(4,2),
	track char(32),
	note varchar(32),
	primary key(id)
);

create table team (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	name char(16),
	primary key(id)
);




