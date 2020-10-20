package br.com.zup.bancodigital.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.zup.bancodigital.domain.model.Conta;
import br.com.zup.bancodigital.domain.model.Transferencia;
import br.com.zup.bancodigital.domain.repository.ContaRepository;
import br.com.zup.bancodigital.domain.repository.TransferenciaRepository;

@Service
public class TransferenciaService {

	@Autowired
	private TransferenciaRepository transferenciaRepository;

	@Autowired
	private ContaRepository contaRepository;
	
	public Transferencia buscar(Long id) {
		return transferenciaRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public Transferencia salvar(Transferencia transferencia) {
		return transferenciaRepository.save(transferencia);
	}
	
	@Transactional
	public void salvar(List<Transferencia> transferencias) {
		for(Transferencia t : transferencias) {
			Optional<Transferencia> tr = transferenciaRepository.findByContaAndCodigo(t.getAgenciaDestino(),t.getContaDestino(), t.getCodigo());
			if(tr.isEmpty()) {
				salvarTransferencia(t);				
			}else {
				System.out.println(String.format("Transferência de código %s já foi processada para a agencia %d conta %d.", 
							t.getCodigo(), t.getAgenciaDestino(), t.getContaDestino()));
			}
		}
	}
	
	@Transactional
	private void salvarTransferencia(Transferencia transferencia) {
		Optional<Conta> contaOptional = contaRepository.findByAgenciaAndNumero(transferencia.getAgenciaDestino(), transferencia.getContaDestino());
		if(contaOptional.isPresent()) {
			Conta conta = contaOptional.get();
			BigDecimal saldo = conta.getSaldo().add(transferencia.getValor());
			conta.setSaldo(saldo);
			salvar(transferencia);
		}else {
			System.out.println(String.format("Conta não encontrada para agência %d conta %d.", 
					transferencia.getAgenciaDestino(), transferencia.getContaDestino()));
		}
	}
	
}
