create table reset_senha_token (
	conta_id bigint not null,
	token varchar(6) not null,
	data_expiracao datetime not null,
	usado tinyint(1) not null default false,
	
	primary key (conta_id),
	constraint fk_reset_senha_token_conta_id foreign key (conta_id) references conta (id)
	
)engine=InnoDB default charset=utf8;