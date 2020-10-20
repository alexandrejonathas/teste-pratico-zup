package br.com.zup.bancodigital.domain.exception;

public class ContaNaoEncontradaException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = 1L;

	public ContaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public ContaNaoEncontradaException(Long id) {
		this(String.format("Não existe um cadastro de cliente com código %d", id));
	}	

}
