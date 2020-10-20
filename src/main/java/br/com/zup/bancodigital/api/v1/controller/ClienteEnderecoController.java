package br.com.zup.bancodigital.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.bancodigital.api.helper.ResourceUriHelper;
import br.com.zup.bancodigital.api.v1.assembler.EnderecoInputDisassembler;
import br.com.zup.bancodigital.api.v1.assembler.EnderecoModelAssembler;
import br.com.zup.bancodigital.api.v1.model.EnderecoModel;
import br.com.zup.bancodigital.api.v1.model.input.EnderecoInput;
import br.com.zup.bancodigital.domain.exception.EntidadeExistenteException;
import br.com.zup.bancodigital.domain.model.Cliente;
import br.com.zup.bancodigital.domain.model.Endereco;
import br.com.zup.bancodigital.domain.service.ClienteService;
import br.com.zup.bancodigital.domain.service.EnderecoService;

@RestController
@RequestMapping("/v1/clientes/{id}/endereco")
public class ClienteEnderecoController {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EnderecoService enderecoService;
	
	@Autowired
	private EnderecoModelAssembler enderecoModelAssembler;
	
	@Autowired
	private EnderecoInputDisassembler enderecoInputDisassembler;
	
	@PostMapping
	public ResponseEntity<EnderecoModel> adicionar(@PathVariable Long id, @RequestBody @Valid EnderecoInput enderecoInput, 
			UriComponentsBuilder uriComponentsBuilder){
		
		Cliente cliente = clienteService.buscarOuFalhar(id);
		
		if(!cliente.semEnderecoCadastrado()) {			
			throw new EntidadeExistenteException("Cliente já possui um endereço cadastrado!");
		}
		
		Endereco endereco = enderecoInputDisassembler.toDomainObject(enderecoInput);
		endereco.setCliente(cliente);
		
		endereco = enderecoService.salvar(endereco);
		
		ResourceUriHelper.addUriInResponseHeader("/v1/clientes/"+cliente.getId()+"/foto-cpf");
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(enderecoModelAssembler.toModel(endereco));
	}
	
}
