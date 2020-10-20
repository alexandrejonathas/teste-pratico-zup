package br.com.zup.bancodigital.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.TransactionSystemException;

import br.com.zup.bancodigital.domain.exception.CepFormatoInvalidoException;
import br.com.zup.bancodigital.domain.model.Cliente;
import br.com.zup.bancodigital.domain.model.Endereco;
import br.com.zup.bancodigital.domain.utils.DatabaseCleaner;

@SpringBootTest
@TestPropertySource("/application-test.properties")
public class EnderecoServiceTest {

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private EnderecoService enderecoService;
	
	private Cliente cliente;
	
	@BeforeEach
	public void init() {
		databaseCleaner.clearTables();
		
		cliente = new Cliente();
		cliente.setCpf("096.330.110-19");
		cliente.setNome("Cliente");
		cliente.setSobrenome("Test");
		cliente.setDataNascimento(LocalDate.of(1984, 03, 03));
		cliente.setEmail("cliente@example.com");		
		
	}	
	
	@Test
	public void deveCadastrarUmEndereco() {
		
		Endereco endereco = new Endereco();
		endereco.setCliente(cliente);
		endereco.setCep("55.038-565");
		endereco.setRua("Rua José Martins Sobrinho, 330");
		endereco.setBairro("Boa Vista");
		endereco.setComplemento("Condomínio Jardim Ipojuca BL 22 AP 003");
		endereco.setCidade("Caruaru");
		endereco.setEstado("Pernambuco");
		
		endereco = enderecoService.salvar(endereco);
		
		assertThat(endereco.getId()).isNotNull();
	}
	
	@Test
	public void deveFalharAoCadastrarUmEnderecoSemCep() {
		
		Endereco endereco = new Endereco();
		endereco.setCliente(cliente);
		endereco.setRua("Rua José Martins Sobrinho, 330");
		endereco.setBairro("Boa Vista");
		endereco.setComplemento("Condomínio Jardim Ipojuca BL 22 AP 003");
		endereco.setCidade("Caruaru");
		endereco.setEstado("Pernambuco");
		
		assertThrows(TransactionSystemException.class, () -> { enderecoService.salvar(endereco); });
	}
	
	@Test
	public void deveFalharAoCadastrarUmEnderecoComCepNoFormatoInvalido() {
		
		Endereco endereco = new Endereco();
		endereco.setCliente(cliente);
		endereco.setCep("55038565");
		endereco.setRua("Rua José Martins Sobrinho, 330");
		endereco.setBairro("Boa Vista");
		endereco.setComplemento("Condomínio Jardim Ipojuca BL 22 AP 003");
		endereco.setCidade("Caruaru");
		endereco.setEstado("Pernambuco");
		
		assertThrows(CepFormatoInvalidoException.class, () -> { enderecoService.salvar(endereco); });
	}	
	
	@Test
	public void deveFalharAoCadastrarUmEnderecoSemRua() {
		
		Endereco endereco = new Endereco();
		endereco.setCliente(cliente);
		endereco.setCep("55.038-565");
		endereco.setBairro("Boa Vista");
		endereco.setComplemento("Condomínio Jardim Ipojuca BL 22 AP 003");
		endereco.setCidade("Caruaru");
		endereco.setEstado("Pernambuco");
		
		assertThrows(TransactionSystemException.class, () -> { enderecoService.salvar(endereco); });
	}	
	
	@Test
	public void deveFalharAoCadastrarUmEnderecoSemBairro() {
		
		Endereco endereco = new Endereco();
		endereco.setCliente(cliente);
		endereco.setCep("55.038-565");
		endereco.setRua("Rua José Martins Sobrinho, 330");
		endereco.setComplemento("Condomínio Jardim Ipojuca BL 22 AP 003");
		endereco.setCidade("Caruaru");
		endereco.setEstado("Pernambuco");
		
		assertThrows(TransactionSystemException.class, () -> { enderecoService.salvar(endereco); });
	}
	
	@Test
	public void deveFalharAoCadastrarUmEnderecoSemComplemento() {
		
		Endereco endereco = new Endereco();
		endereco.setCliente(cliente);
		endereco.setCep("55.038-565");
		endereco.setRua("Rua José Martins Sobrinho, 330");
		endereco.setBairro("Boa Vista");
		endereco.setCidade("Caruaru");
		endereco.setEstado("Pernambuco");
		
		assertThrows(TransactionSystemException.class, () -> { enderecoService.salvar(endereco); });
	}
	
	@Test
	public void deveFalharAoCadastrarUmEnderecoSemCidade() {
		
		Endereco endereco = new Endereco();
		endereco.setCliente(cliente);
		endereco.setCep("55.038-565");
		endereco.setRua("Rua José Martins Sobrinho, 330");
		endereco.setBairro("Boa Vista");
		endereco.setComplemento("Condomínio Jardim Ipojuca BL 22 AP 003");
		endereco.setEstado("Pernambuco");
		
		assertThrows(TransactionSystemException.class, () -> { enderecoService.salvar(endereco); });
	}
	
	@Test
	public void deveFalharAoCadastrarUmEnderecoSemEstado() {
		
		Endereco endereco = new Endereco();
		endereco.setCliente(cliente);
		endereco.setCep("55.038-565");
		endereco.setRua("Rua José Martins Sobrinho, 330");
		endereco.setBairro("Boa Vista");
		endereco.setComplemento("Condomínio Jardim Ipojuca BL 22 AP 003");
		endereco.setCidade("Caruaru");
		
		assertThrows(TransactionSystemException.class, () -> { enderecoService.salvar(endereco); });
	}
	
	@Test
	public void deveFalharAoCadastrarUmEnderecoComClienteInvalido() {
		
		Endereco endereco = new Endereco();
		cliente.setCpf(null);
		endereco.setCliente(cliente);
		endereco.setCep("55.038-565");
		endereco.setRua("Rua José Martins Sobrinho, 330");
		endereco.setBairro("Boa Vista");
		endereco.setComplemento("Condomínio Jardim Ipojuca BL 22 AP 003");
		endereco.setCidade("Caruaru");
		
		assertThrows(TransactionSystemException.class, () -> { enderecoService.salvar(endereco); });
	}	
	
	
}
