ALTER TABLE foto_cpf ADD status varchar(15) DEFAULT 'CRIADO';
ALTER TABLE foto_cpf ADD tentativa_aceite TINYINT(2) default 0;