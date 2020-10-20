package br.com.zup.bancodigital.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.bancodigital.domain.exception.EntidadeNaoEncontradaException;
import br.com.zup.bancodigital.domain.exception.UnprocessableEntityException;
import br.com.zup.bancodigital.domain.service.AceiteFotoCpfService;

@RestController
@RequestMapping("/v1/foto-cpf")
public class AceiteFotoCpfController {
	
	@Autowired
	private AceiteFotoCpfService aceiteFotoCpfService;
	
	@PutMapping("/{id}/aceite")	
	public ResponseEntity<?> aceite(@PathVariable Long id) {
		try {
			
			aceiteFotoCpfService.aceitar(id);
			return ResponseEntity.status(HttpStatus.OK).build();
			
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();	
		} catch (Exception e) {
			aceiteFotoCpfService.atualizaTentativas(id);
			throw new UnprocessableEntityException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}/desistencia")
	public void recusa(@PathVariable Long id) {
		aceiteFotoCpfService.desistir(id);
	}
	
}
