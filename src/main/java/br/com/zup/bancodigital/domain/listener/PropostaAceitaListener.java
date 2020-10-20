package br.com.zup.bancodigital.domain.listener;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import br.com.zup.bancodigital.domain.event.PropostaAceitaEvent;
import br.com.zup.bancodigital.domain.model.Cliente;
import br.com.zup.bancodigital.domain.model.Conta;
import br.com.zup.bancodigital.domain.model.Proposta;
import br.com.zup.bancodigital.domain.service.ContaService;
import br.com.zup.bancodigital.domain.service.EnvioEmailService;
import br.com.zup.bancodigital.domain.service.EnvioEmailService.Mensagem;

@Component
public class PropostaAceitaListener {

	@Autowired
	private EnvioEmailService envioEmailService;
	
	@Autowired
	private ContaService contaService;
	
	@Async
	@TransactionalEventListener
	public void aoAceitarProposta(PropostaAceitaEvent event) throws InterruptedException {
		Proposta proposta = event.getProposta();
		Cliente cliente = proposta.getCliente();
		
		Conta conta = new Conta();
		conta.setProposta(proposta);
		conta.gerarConta();
		
		conta = contaService.salvar(conta);
		
		var mensagem = Mensagem.builder()
				.assunto(cliente.getNome()+ " - Proposta aceita! ")
				.destinatario(cliente.getEmail())
				.corpo("emails/proposta-aceita.html")
				.variavel("conta", conta)
				.variavel("cliente", cliente)
				.variavel("hora", LocalDateTime.now())
				.build();
		
		envioEmailService.enviar(mensagem);		
	}
	
}
