create table cliente (
    id bigint not null auto_increment,
    cpf varchar(14) not null,
    data_nascimento date not null,
    nome varchar(60) not null,
    sobrenome varchar(60) not null,
    email varchar(255) not null,
    
    primary key(id)
)engine=InnoDB default charset=utf8;