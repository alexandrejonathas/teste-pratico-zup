package br.com.zup.bancodigital.domain.exception;

public class CpfFormatoInvalidoException extends NegocioException {

	private static final long serialVersionUID = 1L;
	
	public CpfFormatoInvalidoException(String cpf) {
		super(String.format("O CPF %s deve estar no formato 999.999.999-99", cpf));
	}
		

}
