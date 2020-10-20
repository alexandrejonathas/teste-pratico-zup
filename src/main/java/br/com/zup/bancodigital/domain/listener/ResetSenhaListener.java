package br.com.zup.bancodigital.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.com.zup.bancodigital.domain.event.ResetSenhaEvent;
import br.com.zup.bancodigital.domain.model.Cliente;
import br.com.zup.bancodigital.domain.model.ResetSenhaToken;
import br.com.zup.bancodigital.domain.service.EnvioEmailService;
import br.com.zup.bancodigital.domain.service.EnvioEmailService.Mensagem;

@Component
public class ResetSenhaListener {

	@Autowired
	private EnvioEmailService envioEmailService;	
	
	@Async
	@EventListener
	public void aoSolicitarResetSenha(ResetSenhaEvent event) throws InterruptedException {
		ResetSenhaToken resetSenhaToken = event.getResetSenhaToken();
		Cliente cliente = resetSenhaToken.getConta().getProposta().getCliente();
		
		var mensagem = Mensagem.builder()
				.assunto(cliente.getNome()+ " - Reset de senha! ")
				.destinatario(cliente.getEmail())
				.corpo("emails/reset-senha.html")
				.variavel("cliente", cliente)
				.variavel("token", resetSenhaToken.getToken())
				.build();
		
		envioEmailService.enviar(mensagem);		
	}	
	
}
