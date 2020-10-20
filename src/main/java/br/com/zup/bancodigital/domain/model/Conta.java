package br.com.zup.bancodigital.domain.model;

import java.math.BigDecimal;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Conta {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;		
	
	@OneToOne
	private Proposta proposta;
	
	private Integer agencia;
	
	private Integer numero;
	
	private Integer codigo = 100;
	
	private BigDecimal saldo = BigDecimal.ZERO;
	
	public boolean isNova() {
		return getId() == null;
	}
	
	public void gerarConta() {
		this.gerarAgencia();
		this.gerarNumeroConta();
	}
	
	private void gerarAgencia() {
		String numero = "";
		for(int i = 0; i < 4; i++) {
			numero += String.valueOf(numeroRandomico());
		}
		agencia = Integer.valueOf(numero);
	}
	
	private void gerarNumeroConta() {
		String numeroConta = "";
		for(int i = 0; i < 8; i++) {
			numeroConta += String.valueOf(numeroRandomico());
		}
		numero = Integer.valueOf(numeroConta);
	}
	
	private int numeroRandomico() {
		return new Random().nextInt(9 - 1) + 1;
	}
}
