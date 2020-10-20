package br.com.zup.bancodigital.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.zup.bancodigital.domain.exception.NegocioException;
import br.com.zup.bancodigital.domain.model.Cliente;
import br.com.zup.bancodigital.domain.model.Proposta;
import br.com.zup.bancodigital.domain.repository.PropostaRepository;

@Service
public class FluxoPropostaService {

	@Autowired
	private PropostaService propostaService;
	
	@Autowired
	private PropostaRepository propostaRepository;
	
	@Transactional
	public void aceitar(Long id) {
		Proposta proposta = propostaService.buscarOuFalhar(id);
		
		if(!proposta.isLiberada()) {
			throw new NegocioException(String.format("A proposta %d não está liberada para aceite", proposta.getId()));
		}
		
		this.validarCliente(proposta.getCliente());
		proposta.aceitar();
		
		propostaRepository.save(proposta);
	}
	
	@Transactional
	public void recusar(Long id) {
		Proposta proposta = propostaService.buscarOuFalhar(id);
		proposta.recusar();
		propostaRepository.save(proposta);
	}
	
	private void validarCliente(Cliente cliente) {
		if(cliente.getEndereco() == null) {
			throw new NegocioException("Cliente não possui um endereço cadastrado!");
		}
		
		if(cliente.getCpf() == null) {
			throw new NegocioException("Cliente não realizou o upload do cpf!");
		}
	}
	
	
}
