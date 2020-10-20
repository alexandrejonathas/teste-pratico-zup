package br.com.zup.bancodigital.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.zup.bancodigital.domain.model.FotoCpf;
import br.com.zup.bancodigital.domain.repository.ClienteRepositoryQueries;

@Repository
public class ClienteRepositoryImpl implements ClienteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;		
	
	@Transactional
	@Override
	public FotoCpf save(FotoCpf foto) {
		return manager.merge(foto);
	}

	@Override
	public void delete(FotoCpf foto) {
		manager.remove(foto);		
	}

}
