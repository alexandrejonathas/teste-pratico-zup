package br.com.zup.bancodigital.domain.exception;

public class ResetSenhaTokenNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public ResetSenhaTokenNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public ResetSenhaTokenNaoEncontradoException(Long id) {
		this(String.format("Não existe um cadastro de endereco com código %d", id));
	}	
}
