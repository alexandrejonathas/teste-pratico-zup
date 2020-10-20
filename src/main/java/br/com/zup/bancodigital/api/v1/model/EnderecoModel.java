package br.com.zup.bancodigital.api.v1.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {
	
	private String cep;

	private String rua;

	private String complemento;

	private String bairro;
	
	private String cidade;
	
	private String estado;
}
