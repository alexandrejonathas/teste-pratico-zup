create table proposta (
	id bigint not null auto_increment,
	cliente_id bigint not null,
	aceite tinyint(1),
	status varchar(20) not null,
	
	primary key (id),
	constraint fk_proposta_cliente_id foreign key (cliente_id) references cliente (id)
	
)engine=InnoDB default charset=utf8;