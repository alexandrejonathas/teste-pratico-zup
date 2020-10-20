package br.com.zup.bancodigital.domain.exception;

public class PropostaNaoEncontradaException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = 1L;

	public PropostaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public PropostaNaoEncontradaException(Long id) {
		this(String.format("Não existe um cadastro de cliente com código %d", id));
	}	

}
