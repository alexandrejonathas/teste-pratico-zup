package br.com.zup.bancodigital.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import br.com.zup.bancodigital.core.validation.ValidacaoException;
import br.com.zup.bancodigital.domain.exception.EntidadeEmUsoException;
import br.com.zup.bancodigital.domain.exception.EntidadeNaoEncontradaException;
import br.com.zup.bancodigital.domain.exception.NegocioException;
import br.com.zup.bancodigital.domain.exception.UnprocessableEntityException;

/**
 * 
 * Classe baseada no controller advice do curso ESR da Algaworks
 *
 */

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String MSG_ERRO_GENERICO_USUARIO_FINAL = "Ocorreu um erro inesperado no sistema. "
			+ "Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";

	@Autowired
	private MessageSource messageSource;

	/*@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, WebRequest request){
		HttpStatus status = HttpStatus.FORBIDDEN;
		ProblemaTipo problemaTipo = ProblemaTipo.ACESSO_NEGADO;
		String detail = ex.getMessage();
		
		Problema problema = criarProblemaBuilder(status, problemaTipo, detail)
				.message("Você não possui permissão para executar essa operação.")
				.build();

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);		
	}*/
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex,
			WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemaTipo problemaTipo = ProblemaTipo.RECURSO_NAO_ENCONTRADO;

		Problema problema = criarProblemaBuilder(status, problemaTipo, ex.getMessage()).build();

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemaTipo problemaTipo = ProblemaTipo.ERRO_NEGOCIO;
		Problema problema = criarProblemaBuilder(status, problemaTipo, ex.getMessage()).build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemaTipo problemaTipo = ProblemaTipo.ENTIDADE_EM_USO;
		String detalhe = ex.getMessage();
		Problema problema = criarProblemaBuilder(status, problemaTipo, detalhe).message(detalhe).build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> tratarMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemaTipo problemaTipo = ProblemaTipo.PARAMETRO_INVALIDO;

		String detalhe = String.format(
				"O parâmetro da URL '%s' recebeu o valor '%s', "
						+ "que é inválido. Corrija e informe um valor compatível com o tipo %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problema problema = criarProblemaBuilder(status, problemaTipo, detalhe).build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> tratarErroDeSistema(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemaTipo problemaTipo = ProblemaTipo.ERRO_SISTEMA;
		String detalhe = MSG_ERRO_GENERICO_USUARIO_FINAL;

		Problema problema = criarProblemaBuilder(status, problemaTipo, detalhe).build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<?> tratarValidacaoException(ValidacaoException ex, WebRequest request) {
		return tratarValidacaoIterna(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(UnprocessableEntityException.class)
	public ResponseEntity<?> tratarUnprocessableEntityException(UnprocessableEntityException ex, WebRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ProblemaTipo problemaTipo = ProblemaTipo.ERRO_NEGOCIO;
		Problema problema = criarProblemaBuilder(status, problemaTipo, ex.getMessage()).build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
//	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
//	public ResponseEntity<?> tratarHttpMediaTypeNotSupportedException(){
//		Problema problema = new Problema();
//		problema.setDataHora(LocalDateTime.now());
//		problema.setMensagem("O tipo de mídia não é aceito.");
//		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(problema);		
//	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {

		BindingResult bindingResult = ex.getBindingResult();
		return handleValidationInternal(ex, bindingResult, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {
			return tratarInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}

		if (rootCause instanceof PropertyBindingException) {
			return tratarIgnoredPropertyException((PropertyBindingException) rootCause, headers, status, request);
		}

		ProblemaTipo problemaTipo = ProblemaTipo.MENSAGEM_INCOMPREENSIVEL;
		String detalhe = "O corpo da mensagem está inválido, verifique erro na sintaxe";
		Problema problema = criarProblemaBuilder(status, problemaTipo, detalhe).build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = Problema.builder().title(status.getReasonPhrase()).status(status.value()).build();
		} else if (body instanceof String) {
			body = Problema.builder().title(status.getReasonPhrase()).status(status.value()).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ProblemaTipo problemaTipo = ProblemaTipo.RECURSO_NAO_ENCONTRADO;
		String detalhe = String.format("O recurso %s, que você tentou acessar, é inexistente.", ex.getRequestURL());
		Problema problema = criarProblemaBuilder(status, problemaTipo, detalhe).build();
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return tratarValidacaoIterna(ex, ex.getBindingResult(), headers, status, request);
	}
	
	/**
	 * Exemplo de corpo de resposta com erro, usando Problema Details for HTTP APIs
	 * 
	 * { "status": 400, "type": "http://algafood.com.br/recurso-em-uso", "title":
	 * "Recurso em uso", "detail": "Não foi possível excluir a cozinha de código 8,
	 * porque ela está em uso", "instance": "/cozinhas/8/erros/98204983" }
	 */

	// Criado para evitar duplicação de código no tratamento de ValidacaoException
	private ResponseEntity<Object> tratarValidacaoIterna(Exception ex, BindingResult bindingResult, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return handleValidationInternal(ex, bindingResult, headers, status, request);
	}

	private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Problema.Object> objects = bindingResult.getAllErrors().stream().map(erro -> {
			// Pegando as mensagens do arquivo messages.properties
			String message = messageSource.getMessage(erro, LocaleContextHolder.getLocale());

			// Pega o nome da classe para exibir erros de validação em nivel de classe
			String campo = erro.getObjectName();

			// Verifica se o erro é uma instancia de fielderror para atribuir o nome do campo com erro
			// nos casos de validação nos atributos
			if (erro instanceof FieldError) {
				campo = ((FieldError) erro).getField();
			}
			
			return new Problema.Object(campo, message);
		}).collect(Collectors.toList());

		ProblemaTipo problemaTipo = ProblemaTipo.DADOS_INVALIDOS;
		String detalhe = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
		Problema problema = criarProblemaBuilder(status, problemaTipo, detalhe).message(detalhe).objects(objects)
				.build();
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}

	private Problema.ProblemaBuilder criarProblemaBuilder(HttpStatus status, ProblemaTipo problemaTipo, String detalhe) {
		return Problema.builder().status(status.value()).title(problemaTipo.getTitulo())
				.detail(detalhe).timeStamp(OffsetDateTime.now());
	}

	private ResponseEntity<Object> tratarInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = joiningPath(ex.getPath());

		ProblemaTipo problemaTipo = ProblemaTipo.MENSAGEM_INCOMPREENSIVEL;
		String detalhe = String.format(
				"A propriedade '%s' recebeu o valor '%s', "
						+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		Problema problema = criarProblemaBuilder(status, problemaTipo, detalhe)
				.message(MSG_ERRO_GENERICO_USUARIO_FINAL).build();

		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	private ResponseEntity<Object> tratarIgnoredPropertyException(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = joiningPath(ex.getPath());

		ProblemaTipo problemaTipo = ProblemaTipo.MENSAGEM_INCOMPREENSIVEL;
		String detalhe = String.format(
				"A propriedade %s não existe. " + "Corrija ou remova essa propriedade e tente novamente.", path);
		Problema problema = criarProblemaBuilder(status, problemaTipo, detalhe)
				.message(MSG_ERRO_GENERICO_USUARIO_FINAL).build();

		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	private String joiningPath(List<Reference> references) {
		return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
	}

}
