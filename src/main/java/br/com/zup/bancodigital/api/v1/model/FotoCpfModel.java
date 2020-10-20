package br.com.zup.bancodigital.api.v1.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoCpfModel {

	private String nome;
	
	private String contentType;
	
	private Long tamanho;
	
	private String link;
	
}
