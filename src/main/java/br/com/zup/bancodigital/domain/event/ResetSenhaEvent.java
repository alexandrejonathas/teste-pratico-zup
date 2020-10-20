package br.com.zup.bancodigital.domain.event;

import br.com.zup.bancodigital.domain.model.ResetSenhaToken;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResetSenhaEvent {
	
	private ResetSenhaToken resetSenhaToken;
	
}
