package br.com.zup.bancodigital.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

/*
  	Restrições
	cep obrigatório e no formato adequado
	rua obrigatório
	bairro obrigatório
	complemento obrigatório
	cidade obrigatória
	estado obrigatório
	tudo que é obrigatório do passo 1 precisa estar correto

	Resultado esperado
	status 201 e header location preenchido para o próximo passo do cadastro
	status 400 em caso de falha de qualquer validação e json de retorno com as informações.
 */

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Endereco {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")	
	private Long id;
	
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
	
	@OneToOne(fetch = FetchType.LAZY)
	private Cliente cliente;
	
	public boolean isNovo() {
		return getId() == null;
	}
	
	public boolean cepFormatoValido() {
		String padrao = "^\\d{2}\\.\\d{3}[-]\\d{3}$";
		return getCep() == null ? true : getCep().matches(padrao);
	}
	
}
