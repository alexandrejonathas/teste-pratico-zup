package br.com.zup.bancodigital.domain.repository;

import br.com.zup.bancodigital.domain.model.FotoCpf;

public interface ClienteRepositoryQueries {
	
	FotoCpf save(FotoCpf foto);
	
	void delete(FotoCpf foto);	
	
}
