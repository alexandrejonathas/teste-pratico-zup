package br.com.zup.bancodigital.api.exceptionhandler;

public enum ProblemaTipo {

	ACESSO_NEGADO("/acesso-negado"),
	RECURSO_NAO_ENCONTRADO("Recurso não encontrado"),
	ENTIDADE_EM_USO("Entidade em uso"),
	ERRO_NEGOCIO("Violação de regra de negócio"),
	MENSAGEM_INCOMPREENSIVEL("Mensagem incompreensível"),
	PARAMETRO_INVALIDO("Parâmetro inválido"),
	ERRO_SISTEMA("Erro de sistema"),
	DADOS_INVALIDOS("Dados inválidos");
	
	private String titulo;

	private ProblemaTipo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	
}
