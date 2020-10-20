create table endereco (
    id bigint not null auto_increment,
    cliente_id bigint not null,
    cep varchar(20) not null,
    rua varchar(100) not null,
    complemento varchar(255) not null,
    bairro varchar(100) not null,
    cidade varchar(100) not null,
    estado varchar(60) not null,
    
    primary key(id)
)engine=InnoDB default charset=utf8;

ALTER TABLE endereco ADD CONSTRAINT fk_endereco_cliente_id foreign key (cliente_id) references cliente (id);