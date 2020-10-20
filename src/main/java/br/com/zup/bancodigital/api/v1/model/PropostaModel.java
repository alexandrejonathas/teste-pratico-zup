package br.com.zup.bancodigital.api.v1.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropostaModel {

	private ClienteModel cliente;
	
	private EnderecoModel endereco;
	
	private FotoCpfModel fotoCpf;
	
	private String status;
	
	private Map<String, String> links;
	
}
