package br.com.zup.bancodigital.api.v1.model.input;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferenciaInput {

	@NotNull
	private LocalDate data;
	
	@NotNull
	private BigDecimal valor;
	
	@NotBlank
	private String documento;
	
	@NotBlank
	private String bancoOrigem;
	
	@NotBlank
	private String agenciaOrigem;

	@NotBlank
	private String contaOrigem;
	
	@NotBlank
	private String codigo;
	
	@NotNull
	private Integer agenciaDestino;
	
	@NotBlank
	private Integer contaDestino;
	
}
