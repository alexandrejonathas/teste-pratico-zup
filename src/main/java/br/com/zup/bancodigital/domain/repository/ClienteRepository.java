package br.com.zup.bancodigital.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.zup.bancodigital.domain.model.Cliente;
import br.com.zup.bancodigital.domain.model.FotoCpf;

@Repository
public interface ClienteRepository extends CustomJpaRepository<Cliente, Long>, ClienteRepositoryQueries {

	Optional<Cliente> findByCpf(String cpf);
	
	Optional<Cliente> findByEmail(String email);
	
    @Query("select f from FotoCpf f where f.cliente.id = :clienteId")
    Optional<FotoCpf> findFotoById(Long clienteId);
}
