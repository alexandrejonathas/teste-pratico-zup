set foreign_key_checks = 0;

delete from cliente;
delete from endereco;
delete from foto_cpf;
delete from proposta;
delete from conta;
delete from reset_senha_token;
delete from transferencia;

alter table cliente auto_increment = 1;
alter table endereco auto_increment = 1;
alter table foto_cpf auto_increment = 1;
alter table proposta auto_increment = 1;
alter table conta auto_increment = 1;
alter table reset_senha_token auto_increment = 1;
alter table transferencia auto_increment = 1;

insert into cliente(cpf, data_nascimento, nome, sobrenome, email) 
values 
	('804.121.470-33', '1984-03-03', 'João', 'Silva', 'joao@exemplo.com'),
	('902.281.600-14', '1984-03-03', 'Pedro', 'Lima', 'pedro@exemplo.com'),
	('815.698.750-06', '1984-03-03', 'Maria', 'Morais', 'maria@exemplo.com'),
	('851.811.080-75', '1984-03-03', 'Aquiles', 'Troia', 'aquiles@exemplo.com');
														 
insert into endereco(cep, rua, bairro, complemento, cidade, estado, cliente_id) 
values
	('55.038-565', 'Rua José Martins Sobrinho, 330', 'Boa Vista', 'Condomínio Jardim Ipojuca BL 01 AP 103', 'Caruaru', 'Pernambuco', 1),
	('55.038-565', 'Rua José Martins Sobrinho, 330', 'Boa Vista', 'Condomínio Jardim Ipojuca BL 02 AP 001', 'Caruaru', 'Pernambuco', 2),
	('55.038-565', 'Rua José Martins Sobrinho, 330', 'Boa Vista', 'Condomínio Jardim Ipojuca BL 03 AP 003', 'Caruaru', 'Pernambuco', 3), 
	('55.038-565', 'Rua José Martins Sobrinho, 330', 'Boa Vista', 'Condomínio Jardim Ipojuca BL 22 AP 003', 'Caruaru', 'Pernambuco', 4);
	
insert into foto_cpf(cliente_id, nome, content_type, tamanho, status, tentativa_aceite)
values
	(1, 'logo_informatizza.png', 'image/png', 1024, 'ACEITO', 0),
	(2, 'logo_informatizza.png', 'image/png', 1024, 'ACEITO', 0),
	(3, 'logo_informatizza.png', 'image/png', 1024, 'CRIADO', 0),
	(4, 'logo_informatizza.png', 'image/png', 1024, 'CRIADO', 0);
	
insert into proposta (cliente_id, aceite, status)
values
	(1, true, 'LIBERADA'),
	(2, true, 'LIBERADA'),
	(3, null, 'CRIADA'),
	(4, null, 'CRIADA');
	
insert into conta(proposta_id, codigo, agencia, numero, saldo)
values
	(1, 100, 1414, 14141414, 0),
	(2, 100, 1515, 15151515, 0);	
	