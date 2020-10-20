create table foto_cpf (
	cliente_id bigint not null,
	nome varchar(150) not null,
	content_type varchar(80) not null,
	tamanho int not null,
	
	primary key (cliente_id),
	constraint fk_foto_cpf_cliente foreign key (cliente_id) references cliente (id)
	
)engine=InnoDB default charset=utf8;