package br.com.zup.bancodigital.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusProposta {

	CRIADA("Criada"), PENDENTE("Pendente", CRIADA), LIBERADA("Liberada", CRIADA, PENDENTE), CANCELADA("Cancelada", CRIADA, PENDENTE, LIBERADA);
	
	private String descricao;
	
	private StatusProposta(String descricao, StatusProposta ...statusAnteriores) {
		this.descricao = descricao;
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	private List<StatusProposta> statusAnteriores;
	
	public boolean naoPodeAlterarPara(StatusProposta novoStatus) {
		return !novoStatus.statusAnteriores.contains(this);
	}
	
	public boolean podeAlterarPara(StatusProposta novoStatus) {
		return !naoPodeAlterarPara(novoStatus);
	}		
	
}
