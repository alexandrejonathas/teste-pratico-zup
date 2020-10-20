package br.com.zup.bancodigital.api.v1.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.zup.bancodigital.api.helper.ResourceUriHelper;
import br.com.zup.bancodigital.api.v1.assembler.FotoCpfModelAssembler;
import br.com.zup.bancodigital.api.v1.model.FotoCpfModel;
import br.com.zup.bancodigital.api.v1.model.input.FotoCpfInput;
import br.com.zup.bancodigital.domain.exception.EntidadeNaoEncontradaException;
import br.com.zup.bancodigital.domain.exception.NegocioException;
import br.com.zup.bancodigital.domain.exception.UnprocessableEntityException;
import br.com.zup.bancodigital.domain.model.Cliente;
import br.com.zup.bancodigital.domain.model.FotoCpf;
import br.com.zup.bancodigital.domain.model.Proposta;
import br.com.zup.bancodigital.domain.service.ClienteService;
import br.com.zup.bancodigital.domain.service.FotoCpfService;
import br.com.zup.bancodigital.domain.service.FotoStorageService;
import br.com.zup.bancodigital.domain.service.FotoStorageService.FotoRecuperada;
import br.com.zup.bancodigital.domain.service.PropostaService;

@RestController
@RequestMapping("/v1/clientes/{clienteId}/foto-cpf")
public class ClienteFotoCpfController {

	@Autowired
	private FotoCpfService fotoCpfService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private PropostaService propostaService;
	
	@Autowired
	private FotoStorageService fotoStorage;
	
	@Autowired
	private FotoCpfModelAssembler fotoCpfModelAssembler;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoCpfModel buscar(@PathVariable Long clienteId) {
		
		FotoCpf fotoCpf = fotoCpfService.buscarOuFalhar(clienteId);
		
		return fotoCpfModelAssembler.toModel(fotoCpf);
	}
	
	@GetMapping
	public ResponseEntity<?> servir(@PathVariable Long clienteId, @RequestHeader(name = "accept") String acceptHeader)
			throws HttpMediaTypeNotAcceptableException {
		
		try {
			FotoCpf foto = fotoCpfService.buscarOuFalhar(clienteId);
			
			MediaType mediaTypeFoto = MediaType.parseMediaType(foto.getContentType());
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
			
			verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);
			
			FotoRecuperada fotoRecuperada = fotoStorage.recuperar(foto.getNome());
			
			if(fotoRecuperada.temUrl()) {
				return ResponseEntity
						.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl()).build();
			}else {				
				return ResponseEntity.ok()
						.contentType(mediaTypeFoto)
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));
			}
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> cadastrarFoto( @PathVariable Long clienteId, @Valid FotoCpfInput fotoCpfInput) throws IOException {
		try {			
			Cliente cliente = clienteService.buscarOuFalhar(clienteId);
			
			if(cliente.semEnderecoCadastrado()) {
				throw new NegocioException("O cliente não possui um endereço cadastrado");
			}
			
			MultipartFile arquivo = fotoCpfInput.getArquivo();
			
			if(!cliente.semFotoCpfCadastrada()) {
				throw new NegocioException(String.format("O cliente de código %d já possui uma foto de cpf cadastrada.", cliente.getId()));
			}
			
			FotoCpf foto = new FotoCpf();
			foto.setCliente(cliente);
			foto.setContentType(arquivo.getContentType());		
			foto.setTamanho(arquivo.getSize());
			
			foto.setNome(arquivo.getOriginalFilename());
			
			foto = fotoCpfService.salvar(foto, arquivo.getInputStream());		
			cliente.setFotoCpf(foto);
			
			Proposta proposta = new Proposta();
			proposta.setCliente(cliente);
			
			proposta = propostaService.salvar(proposta);
			
			ResourceUriHelper.addUriInResponseHeader("/v1/propostas/"+proposta.getId());
			
			return ResponseEntity.status(HttpStatus.CREATED).body(fotoCpfModelAssembler.toModel(foto));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}catch (NegocioException e) {
			throw new UnprocessableEntityException(e.getMessage());
		}
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long clienteId) {
		fotoCpfService.deletar(clienteId);
	}	
	
	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) 
			throws HttpMediaTypeNotAcceptableException {
		
		boolean compativel = mediaTypesAceitas.stream()
								.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
		if(!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}
	}	
	
	
}
