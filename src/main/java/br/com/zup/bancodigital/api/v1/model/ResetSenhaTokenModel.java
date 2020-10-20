package br.com.zup.bancodigital.api.v1.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetSenhaTokenModel {

	private String token;
	
	private LocalDateTime dataExpiracao;
	
	private boolean usado;
	
}
