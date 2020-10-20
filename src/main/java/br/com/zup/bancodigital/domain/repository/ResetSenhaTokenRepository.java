package br.com.zup.bancodigital.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.zup.bancodigital.domain.model.ResetSenhaToken;

@Repository
public interface ResetSenhaTokenRepository extends CustomJpaRepository<ResetSenhaToken, Long> {

	Optional<ResetSenhaToken> findByToken(String token);
	
}
