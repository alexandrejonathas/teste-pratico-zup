package br.com.zup.bancodigital.core.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("zup-bd.reset-senha-token")
public class ResetSenhaTokenProperties {

	@NonNull
	private Long expiracao;
	
}
