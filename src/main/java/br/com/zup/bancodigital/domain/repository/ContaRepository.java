package br.com.zup.bancodigital.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.zup.bancodigital.domain.model.Conta;

@Repository
public interface ContaRepository extends CustomJpaRepository<Conta, Long> {

	@Query("select c from Conta c join c.proposta p join p.cliente cl "
			+ "where cl.email = :email and cl.cpf = :cpf")
	Optional<Conta> findByEmailAndCpf(String email, String cpf);
	
	@Query("select c from Conta c where agencia = :agencia and numero = :numero")
	Optional<Conta> findByAgenciaAndNumero(Integer agencia, Integer numero);	
	
}
