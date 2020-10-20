package br.com.zup.bancodigital.domain.exception;

public class CepFormatoInvalidoException extends NegocioException {

	private static final long serialVersionUID = 1L;
	
	public CepFormatoInvalidoException(String cep) {
		super(String.format("O CEP %s deve estar no formato 99.999-999", cep));
	}
		

}
