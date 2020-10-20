create table conta (
	id bigint not null auto_increment,
	proposta_id bigint not null,
	agencia integer(4) not null,
	numero integer(8) not null,
	codigo integer(3) not null,
	saldo decimal(10,2) not null default 0,
	
	primary key (id),
	constraint fk_conta_proposta_id foreign key (proposta_id) references proposta (id)
	
)engine=InnoDB default charset=utf8;