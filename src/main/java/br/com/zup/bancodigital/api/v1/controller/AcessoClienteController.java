package br.com.zup.bancodigital.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.bancodigital.domain.exception.EntidadeNaoEncontradaException;
import br.com.zup.bancodigital.domain.exception.NegocioException;
import br.com.zup.bancodigital.domain.model.Conta;
import br.com.zup.bancodigital.domain.model.ResetSenhaToken;
import br.com.zup.bancodigital.domain.service.ContaService;
import br.com.zup.bancodigital.domain.service.ResetSenhaService;

@RestController
@RequestMapping("/v1/cliente")
public class AcessoClienteController {

	@Autowired
	private ContaService contaService;
	
	@Autowired
	private ResetSenhaService resetSenhaService;

	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping("resetar-senha")
	public void resetarSenha(@RequestParam String email, @RequestParam String cpf) {
		try {			
			Conta conta = contaService.buscarPorEmailECpfOuFalhar(email, cpf);
			resetSenhaService.criarToken(conta);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@PutMapping("alterar-senha")
	public void alterarSenha(@RequestParam(required = true) String token, @RequestParam(required = true) String senha) {
		try {			
			ResetSenhaToken resetSenhaToken = resetSenhaService.buscarOuFalhar(token);
			resetSenhaService.alterarSenha(resetSenhaToken, senha);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}	
	
}
