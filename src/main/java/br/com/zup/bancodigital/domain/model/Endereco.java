package br.com.zup.bancodigital.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Endereco {

	@EqualsAndHashCode.Include
	@Id
	@Column(name = "cliente_id")
	private Long id;
	
	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	private Cliente cliente;	
	
	@NotBlank
	@Column(nullable = false)
	private String cep;
	
	@NotBlank
	@Column(nullable = false)
	private String rua;
	
	@NotBlank
	@Column(nullable = false)
	private String bairro;
	
	@NotBlank
	@Column(nullable = false)
	private String complemento;
	
	@NotBlank
	@Column(nullable = false)
	private String cidade;
	
	@NotBlank
	@Column(nullable = false)
	private String estado;
	
	public boolean isNovo() {
		return getId() == null;
	}
	
	public boolean cepFormatoValido() {
		String padrao = "^\\d{2}\\.\\d{3}[-]\\d{3}$";
		return getCep() == null ? true : getCep().matches(padrao);
	}
	
}
