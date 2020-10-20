package br.com.zup.bancodigital.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import br.com.zup.bancodigital.domain.event.PropostaRecusadaEvent;
import br.com.zup.bancodigital.domain.model.Cliente;
import br.com.zup.bancodigital.domain.model.Proposta;
import br.com.zup.bancodigital.domain.service.EnvioEmailService;
import br.com.zup.bancodigital.domain.service.EnvioEmailService.Mensagem;

@Component
public class PropostaRecusadaListener {

	@Autowired
	private EnvioEmailService envioEmailService;
	
	@Async
	@TransactionalEventListener
	public void aoAceitarProposta(PropostaRecusadaEvent event) {
		Proposta proposta = event.getProposta();
		Cliente cliente = proposta.getCliente();
		
		var mensagem = Mensagem.builder()
				.assunto(cliente.getNome()+ " - Proposta recusada!")
				.destinatario(cliente.getEmail())
				.corpo("emails/proposta-recusada.html")
				.variavel("cliente", cliente)
				.build();
		
		envioEmailService.enviar(mensagem);		
	}	
	
}
