package br.com.zup.bancodigital.domain.exception;

public class FotoCpfNaoEncontradaException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = 1L;

	public FotoCpfNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public FotoCpfNaoEncontradaException(Long id) {
		this(String.format("Não existe um cadastro de foto do cliente com código %d", id));
	}	

}
