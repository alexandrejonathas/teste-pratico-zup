package br.com.zup.bancodigital.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.bancodigital.api.helper.ResourceUriHelper;
import br.com.zup.bancodigital.api.v1.assembler.ClienteInputDisassembler;
import br.com.zup.bancodigital.api.v1.assembler.ClienteModelAssembler;
import br.com.zup.bancodigital.api.v1.model.ClienteModel;
import br.com.zup.bancodigital.api.v1.model.input.ClienteInput;
import br.com.zup.bancodigital.domain.model.Cliente;
import br.com.zup.bancodigital.domain.service.ClienteService;

@RestController
@RequestMapping("/v1/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteModelAssembler clienteModelAssembler;
	
	@Autowired
	private ClienteInputDisassembler clienteInputDisassembler;	
	
	
	@PostMapping
	public ResponseEntity<ClienteModel> criar(@RequestBody @Valid ClienteInput clienteInput) {
		Cliente cliente = clienteService.salvar(clienteInputDisassembler.toDomainObject(clienteInput));
		
		ResourceUriHelper.addUriInResponseHeader("/v1/clientes/"+cliente.getId()+"/endereco");
		
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteModelAssembler.toModel(cliente));
	}
	
}
