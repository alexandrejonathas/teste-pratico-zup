package br.com.zup.bancodigital.api.v1.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.bancodigital.api.v1.assembler.PropostaModelAssembler;
import br.com.zup.bancodigital.api.v1.model.PropostaModel;
import br.com.zup.bancodigital.domain.exception.NegocioException;
import br.com.zup.bancodigital.domain.exception.UnprocessableEntityException;
import br.com.zup.bancodigital.domain.model.Proposta;
import br.com.zup.bancodigital.domain.service.FluxoPropostaService;
import br.com.zup.bancodigital.domain.service.PropostaService;

@RestController
@RequestMapping("/v1/propostas")
public class PropostaController {

	@Autowired
	private PropostaService propostaService;
	
	@Autowired
	private FluxoPropostaService fluxoPropostaService;
	
	@Autowired
	private PropostaModelAssembler propostaModelAssembler;
	
	@GetMapping("/{id}")
	public PropostaModel buscar(@PathVariable Long id){
		Proposta proposta = propostaService.buscarOuFalhar(id);
		return propostaModelAssembler.toModel(proposta);
	}
	
	@PutMapping("/{id}/aceite")
	public ResponseEntity<?> aceitar(@PathVariable Long id) {
		try {
			fluxoPropostaService.aceitar(id);
			return ResponseEntity.ok("A conta será criada"+LocalDateTime.now());
		} catch (NegocioException e) {
			throw new UnprocessableEntityException(e.getMessage());
		}
	}
	
	@PutMapping("/{id}/recusa")
	public ResponseEntity<?> recusar(@PathVariable Long id) {
		try {
			fluxoPropostaService.recusar(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (NegocioException e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}
	}	
	
}
