package br.com.zup.bancodigital.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.zup.bancodigital.domain.exception.ContaNaoEncontradaException;
import br.com.zup.bancodigital.domain.model.Conta;
import br.com.zup.bancodigital.domain.repository.ContaRepository;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;
	
	public Conta buscarOuFalhar(Long id) {
		return contaRepository.findById(id).orElseThrow(() -> new ContaNaoEncontradaException(id));
	}
	
	public Conta buscarPorEmailECpfOuFalhar(String email, String cpf) {
		return contaRepository.findByEmailAndCpf(email, cpf)
				.orElseThrow(() -> new ContaNaoEncontradaException(String.format("Conta não encontrada para o email %s e cpf %s.", email, cpf)));
	}
	
	@Transactional
	public Conta salvar(Conta conta) {
		return contaRepository.save(conta);
	}
	
	
}
