package br.com.zup.bancodigital.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.zup.bancodigital.domain.exception.NegocioException;
import br.com.zup.bancodigital.domain.exception.PropostaNaoEncontradaException;
import br.com.zup.bancodigital.domain.model.Proposta;
import br.com.zup.bancodigital.domain.repository.PropostaRepository;

@Service
public class PropostaService {

	@Autowired
	private PropostaRepository propostaRepository;
	
	public Proposta buscarOuFalhar(Long id) {
		return propostaRepository.findById(id).orElseThrow(() -> new PropostaNaoEncontradaException(id));
	}
	
	@Transactional
	public Proposta salvar(Proposta proposta) {
		
		if(proposta.getCliente() == null) {
			throw new NegocioException("O cliente da proposta é obrigatorio!");
		}
		
		return propostaRepository.save(proposta);
	}
	
}
