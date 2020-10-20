package br.com.zup.bancodigital.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.bancodigital.api.v1.assembler.TransferenciaInputDisassembler;
import br.com.zup.bancodigital.api.v1.model.input.TransferenciaInput;
import br.com.zup.bancodigital.domain.model.Transferencia;
import br.com.zup.bancodigital.domain.service.TransferenciaService;

@RestController
@RequestMapping("/v1/transferencias")
public class TransferenciaController {

	@Autowired
	private TransferenciaService transferenciaService;
	
	@Autowired
	private TransferenciaInputDisassembler transferenciaInputDisassembler;
	
	@PostMapping
	public ResponseEntity<?> receber(@RequestBody @Valid List<TransferenciaInput> transferenciasInput){
		
		List<Transferencia> transferencias = transferenciaInputDisassembler.toDomainObjectList(transferenciasInput);
		
		transferenciaService.salvar(transferencias);
		return ResponseEntity.ok().build();
	}
	
}
