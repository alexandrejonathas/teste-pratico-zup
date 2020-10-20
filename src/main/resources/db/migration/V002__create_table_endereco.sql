create table endereco (
    cliente_id bigint not null,
    cep varchar(20) not null,
    rua varchar(100) not null,
    complemento varchar(255) not null,
    bairro varchar(100) not null,
    cidade varchar(100) not null,
    estado varchar(60) not null,
    
    primary key(cliente_id),
    constraint fk_enderco_cliente_id foreign key (cliente_id) references cliente (id)
)engine=InnoDB default charset=utf8;