package br.com.zup.bancodigital.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.zup.bancodigital.domain.model.Transferencia;

@Repository
public interface TransferenciaRepository extends CustomJpaRepository<Transferencia, Long> {

	@Query("select t from Transferencia t "
			+ "where t.agenciaDestino = :agenciaDestino and t.contaDestino = :contaDestino "
			+ "and t.codigo = :codigo")
	Optional<Transferencia> findByContaAndCodigo(Integer agenciaDestino, Integer contaDestino, String codigo);	
	
}
