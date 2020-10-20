package br.com.zup.bancodigital.domain.service;

import java.io.InputStream;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.bancodigital.domain.exception.FotoCpfNaoEncontradaException;
import br.com.zup.bancodigital.domain.model.Cliente;
import br.com.zup.bancodigital.domain.model.FotoCpf;
import br.com.zup.bancodigital.domain.repository.ClienteRepository;
import br.com.zup.bancodigital.domain.service.FotoStorageService.NovaFoto;

@Service
public class FotoCpfService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	public FotoCpf buscarOuFalhar(Long clienteId) {
		return clienteRepository.findFotoById(clienteId).orElseThrow(() -> new FotoCpfNaoEncontradaException(clienteId));
	}
	
	@Transactional
	public FotoCpf salvar(FotoCpf foto, InputStream inputStream) {
		Cliente cliente = foto.getCliente();
		
		Long clienteId = cliente.getId();
		String nomeArquivo = fotoStorageService.gerarNomeArquivo(foto.getNome());
		String nomeArquivoExistente = null;
		
		Optional<FotoCpf> optionalFotoCpf = clienteRepository.findFotoById(clienteId);
		
		if(optionalFotoCpf.isPresent()) {
			FotoCpf fc = optionalFotoCpf.get();
			nomeArquivoExistente = fc.getNome();
			clienteRepository.delete(fc);
			clienteRepository.flush();
		}
		
		foto.setNome(nomeArquivo);
		foto = clienteRepository.save(foto);
		clienteRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNome())
				.contentType(foto.getContentType())
				.inputStream(inputStream).build();
		
		fotoStorageService.substituir(nomeArquivoExistente, novaFoto);
		
		return foto;
	}
	
	@Transactional
	public void deletar(Long clienteId) {
		FotoCpf foto = this.buscarOuFalhar(clienteId);
		
		String nome = foto.getNome();
		
		clienteRepository.delete(foto);
		clienteRepository.flush();
		
		fotoStorageService.remover(nome);
	}	
	
	
}
