package br.com.zup.bancodigital.domain.exception;

public class EnderecoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public EnderecoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public EnderecoNaoEncontradoException(Long id) {
		this(String.format("Não existe um cadastro de endereco com código %d", id));
	}	
}
