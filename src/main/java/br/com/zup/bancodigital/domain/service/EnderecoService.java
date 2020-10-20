package br.com.zup.bancodigital.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.zup.bancodigital.domain.exception.CepFormatoInvalidoException;
import br.com.zup.bancodigital.domain.exception.EnderecoNaoEncontradoException;
import br.com.zup.bancodigital.domain.model.Endereco;
import br.com.zup.bancodigital.domain.repository.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Endereco buscarOuFalhar(Long id) {
		return enderecoRepository.findById(id).orElseThrow(() -> new EnderecoNaoEncontradoException(id));
	}
	
	@Transactional
	public Endereco salvar(Endereco endereco) {
		
		if(!endereco.cepFormatoValido()) {
			throw new CepFormatoInvalidoException(endereco.getCep());
		}
		
		return enderecoRepository.save(endereco);
	}
	
}
