package br.com.zup.bancodigital.domain.repository;

import org.springframework.stereotype.Repository;

import br.com.zup.bancodigital.domain.model.Proposta;

@Repository
public interface PropostaRepository extends CustomJpaRepository<Proposta, Long> {

}
