create table transferencia (
	id bigint not null auto_increment,
	data date not null,
	valor decimal(10,2) not null,
	documento varchar(20) not null,
	banco_origem varchar(10) not null,
	conta_origem varchar(10) not null,
	agencia_origem varchar(10) not null,
	codigo varchar(100) not null,
	agencia_destino integer(4) not null,
	conta_destino integer(8) not null,
	primary key (id)
	
)engine=InnoDB default charset=utf8;