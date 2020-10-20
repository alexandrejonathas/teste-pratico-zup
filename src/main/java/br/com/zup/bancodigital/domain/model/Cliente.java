package br.com.zup.bancodigital.domain.model;

import java.time.LocalDate;
import java.time.Period;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cliente {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;	
	
	@CPF
	@NotBlank
	@Column(nullable = false)
	private String cpf;
	
	@NotNull
	@Column(nullable = false)
	private LocalDate dataNascimento;
	
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@NotBlank
	@Column(nullable = false)
	private String sobrenome;
	
	@Email
	@NotBlank
	@Column(nullable = false)
	private String email;
	
	@OneToOne(mappedBy = "cliente")
	private Endereco endereco;
	
	@OneToOne(mappedBy = "cliente")
	private FotoCpf fotoCpf;
	
	@OneToOne(mappedBy = "cliente", fetch = FetchType.LAZY)
	private Proposta proposta;
	
	private String senha;
	
	public boolean isNovo() {
		return getId() == null;
	}
	
	public boolean cpfTemFormatoValido() {
		String padrao = "\\d{3}\\.\\d{3}\\.\\d{3}[-]\\d{2}";
		return getCpf() == null ? true : getCpf().matches(padrao);
	}
	
	public boolean isMaiorQueDezoitoAnos() {
		if (getDataNascimento() == null) 
			return false; 
		int anos = Period.between(getDataNascimento(), LocalDate.now()).getYears();
		return anos > 18;
	}
	
	public boolean semEnderecoCadastrado() {
		return getEndereco() == null;
	}
	
	public boolean semFotoCpfCadastrada() {
		return getFotoCpf() == null;
	}
	
	
}
