package br.com.zup.bancodigital.api.v1.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteModel {

	private Long id;
	
	private String cpf;
	
	private LocalDate dataNascimento;
	
	private String nome;
	
	private String email;
	
}
