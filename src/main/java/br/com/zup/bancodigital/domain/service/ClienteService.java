package br.com.zup.bancodigital.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.zup.bancodigital.domain.exception.ClienteNaoEncontradoException;
import br.com.zup.bancodigital.domain.exception.CpfFormatoInvalidoException;
import br.com.zup.bancodigital.domain.exception.EntidadeExistenteException;
import br.com.zup.bancodigital.domain.exception.NegocioException;
import br.com.zup.bancodigital.domain.model.Cliente;
import br.com.zup.bancodigital.domain.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente buscarOuFalhar(Long id) {
		return clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
	}
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		
		//clienteRepository.detach(cliente);
		
		validarCliente(cliente);
		
		return clienteRepository.save(cliente);
	}	
	
	private void validarCliente(Cliente cliente) {
		if(!cliente.isMaiorQueDezoitoAnos()) {
			throw new NegocioException("É precisor ser maior de 18 anos para criar uma conta!");
		}
		
		if(!cliente.cpfTemFormatoValido()) {
			throw new CpfFormatoInvalidoException(cliente.getCpf());
		}
		
		Optional<Cliente> optional = clienteRepository.findByCpf(cliente.getCpf());
		if(optional.isPresent() && !optional.get().equals(cliente)) {
			throw new EntidadeExistenteException(String.format("O cpf %s já está cadastrado", cliente.getCpf()));
		}
		
		optional = clienteRepository.findByEmail(cliente.getEmail());
		if(optional.isPresent() && !optional.get().equals(cliente)) {
			throw new EntidadeExistenteException(String.format("O e-mail %s já está cadastrado", cliente.getEmail()));
		}			
	}
	
}
