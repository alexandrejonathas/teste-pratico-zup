package br.com.zup.bancodigital.domain.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.zup.bancodigital.core.security.ResetSenhaTokenProperties;
import br.com.zup.bancodigital.domain.event.ResetSenhaEvent;
import br.com.zup.bancodigital.domain.exception.NegocioException;
import br.com.zup.bancodigital.domain.exception.ResetSenhaTokenNaoEncontradoException;
import br.com.zup.bancodigital.domain.model.Cliente;
import br.com.zup.bancodigital.domain.model.Conta;
import br.com.zup.bancodigital.domain.model.ResetSenhaToken;
import br.com.zup.bancodigital.domain.repository.ResetSenhaTokenRepository;

@Service
public class ResetSenhaService {

	@Autowired
	private ResetSenhaTokenProperties resetSenhaTokenProperties;
	
	@Autowired
	private ResetSenhaTokenRepository resetSenhaTokenRepository;

	@Autowired
	private PasswordValidator passwordValidator;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;		
	
	public ResetSenhaToken buscar(Long id) {
		return resetSenhaTokenRepository.findById(id).orElse(null);
	}	
	
	public ResetSenhaToken buscarOuFalhar(String token) {
		return resetSenhaTokenRepository.findByToken(token)
				.orElseThrow(() -> new ResetSenhaTokenNaoEncontradoException(String.format("Token %s não encontrado", token)));
	}
	
	@Transactional
	public ResetSenhaToken salvar(ResetSenhaToken resetSenhaToken) {
		return resetSenhaTokenRepository.save(resetSenhaToken);
	}

	@Transactional
	public ResetSenhaToken criarToken(Conta conta) {
		
		ResetSenhaToken resetSenhaToken = buscar(conta.getId());
		
		if(resetSenhaToken == null) {
			resetSenhaToken = new ResetSenhaToken();
			resetSenhaToken.setConta(conta);
		}
		String token = UUID.randomUUID().toString();
		resetSenhaToken.setToken(token.substring(0, 6));		
		
		Long expiracao = resetSenhaTokenProperties.getExpiracao();
		
		LocalDateTime dataExpiracao = LocalDateTime.now();
		dataExpiracao = dataExpiracao.plusMinutes(expiracao);
		
		resetSenhaToken.setDataExpiracao(dataExpiracao);
		resetSenhaToken.setUsado(false);
		
		resetSenhaToken = resetSenhaTokenRepository.save(resetSenhaToken);
		
		applicationEventPublisher.publishEvent(new ResetSenhaEvent(resetSenhaToken));
		
		return resetSenhaToken;
	}
	
	@Transactional
	public void alterarSenha(ResetSenhaToken resetSenhaToken, String senha) {
		validarToken(resetSenhaToken);
		
		RuleResult result = passwordValidator.validate(new PasswordData(senha));
		
		if(!result.isValid()) {
			throw new NegocioException("A senha não atende aos requisitos mínimos, "
					+ "a senha deve ter 8 caracteres entre eles "
					+ "deve conter uma letra maiúscula, uma minúscula, um número e um caracter especial.");
		}
		
		Cliente cliente = resetSenhaToken.getConta().getProposta().getCliente();
		cliente.setSenha(passwordEncoder.encode(senha));
		
		resetSenhaToken.setUsado(true);
		resetSenhaTokenRepository.save(resetSenhaToken);
	}	
	
	private void validarToken(ResetSenhaToken resetSenhaToken) {
		
		if(resetSenhaToken.isUsado()) {
			throw new NegocioException("O Token já foi utilizado");
		}
		
		if(resetSenhaToken.hasExpired()) {
			throw new NegocioException("Token expirado");
		}		
	}

	
}
