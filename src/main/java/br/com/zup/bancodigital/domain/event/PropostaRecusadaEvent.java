package br.com.zup.bancodigital.domain.event;

import br.com.zup.bancodigital.domain.model.Proposta;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PropostaRecusadaEvent {

	private Proposta proposta;
	
}
