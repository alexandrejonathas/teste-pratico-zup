package br.com.zup.bancodigital.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.zup.bancodigital.domain.event.PropostaLiberadaEvent;
import br.com.zup.bancodigital.domain.exception.FotoCpfNaoEncontradaException;
import br.com.zup.bancodigital.domain.model.Cliente;
import br.com.zup.bancodigital.domain.model.FotoCpf;
import br.com.zup.bancodigital.domain.repository.ClienteRepository;

@Service
public class AceiteFotoCpfService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteService clienteService;
	
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;	
	
	public FotoCpf buscarOuFalhar(Long id) {
		Cliente cliente = clienteService.buscarOuFalhar(id);
		FotoCpf fotoCpf = cliente.getFotoCpf();
		
		if(fotoCpf == null) {
			throw new FotoCpfNaoEncontradaException(id);
		}
		
		return fotoCpf;
	}
	
	@Transactional
	public FotoCpf aceitar(Long id) {
		FotoCpf fotoCpf = buscarOuFalhar(id);
		fotoCpf.aceite();
		fotoCpf = clienteRepository.save(fotoCpf);
		
		applicationEventPublisher.publishEvent(new PropostaLiberadaEvent(fotoCpf.getCliente().getProposta()));
		
		return fotoCpf;
	}
	
	@Transactional
	public FotoCpf desistir(Long id) {
		FotoCpf fotoCpf = buscarOuFalhar(id);
		fotoCpf.desistir();
		fotoCpf = clienteRepository.save(fotoCpf);
		return fotoCpf;
	}
	
	@Transactional
	public void atualizaTentativas(Long id) {
		FotoCpf fotoCpf = buscarOuFalhar(id);
		fotoCpf.atualizarTentativas();
		clienteRepository.save(fotoCpf);
	}
	
}
