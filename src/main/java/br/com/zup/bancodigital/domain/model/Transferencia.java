package br.com.zup.bancodigital.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Transferencia {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	@NotNull
	private LocalDate data;
	
	@NotNull
	private BigDecimal valor;
	
	@NotBlank
	private String documento;
	
	@NotBlank
	private String bancoOrigem;
	
	@NotBlank
	private String contaOrigem;
	
	@NotBlank
	private String agenciaOrigem;
	
	@NotBlank
	private String codigo;
	
	@NotNull
	private Integer contaDestino;
	
	@NotNull
	private Integer agenciaDestino;	
	
	public boolean isNova() {
		return getId() == null;
	}
	
}
