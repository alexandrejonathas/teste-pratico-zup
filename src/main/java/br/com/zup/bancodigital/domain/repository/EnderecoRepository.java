package br.com.zup.bancodigital.domain.repository;

import org.springframework.stereotype.Repository;

import br.com.zup.bancodigital.domain.model.Endereco;

@Repository
public interface EnderecoRepository extends CustomJpaRepository<Endereco, Long> {

}
