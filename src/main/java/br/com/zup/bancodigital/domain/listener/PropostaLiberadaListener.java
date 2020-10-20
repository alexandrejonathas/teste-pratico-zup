package br.com.zup.bancodigital.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.zup.bancodigital.domain.event.PropostaLiberadaEvent;
import br.com.zup.bancodigital.domain.model.Proposta;
import br.com.zup.bancodigital.domain.service.PropostaService;

@Component
public class PropostaLiberadaListener {

	@Autowired
	private PropostaService propostaService;
	
	@EventListener
	public void aoLiberarProposta(PropostaLiberadaEvent event) {
		Proposta proposta = event.getProposta();
		proposta.liberar();
		propostaService.salvar(proposta);
		System.out.println("Liberando a proposta");
	}
	
}
