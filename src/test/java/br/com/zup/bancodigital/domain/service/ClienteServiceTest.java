package br.com.zup.bancodigital.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.TransactionSystemException;

import br.com.zup.bancodigital.domain.exception.CpfFormatoInvalidoException;
import br.com.zup.bancodigital.domain.exception.EntidadeExistenteException;
import br.com.zup.bancodigital.domain.exception.NegocioException;
import br.com.zup.bancodigital.domain.model.Cliente;
import br.com.zup.bancodigital.domain.utils.DatabaseCleaner;

@SpringBootTest
@TestPropertySource("/application-test.properties")
public class ClienteServiceTest {

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private ClienteService clienteService;
	
	@BeforeEach
	public void init() {
		databaseCleaner.clearTables();
	}
	
	@Test
	public void deveCadastrarUmCliente() {
		Cliente cliente = new Cliente();
		cliente.setCpf("096.330.110-19");
		cliente.setNome("Cliente");
		cliente.setSobrenome("Test");
		cliente.setDataNascimento(LocalDate.of(1984, 03, 03));
		cliente.setEmail("cliente@example.com");
		
		cliente = clienteService.salvar(cliente);
		
		assertThat(cliente.getId()).isNotNull();
	}
	
	@Test
	public void deveFalharAoCadastrarUmClienteSemCpf() {
		Cliente cliente = new Cliente();
		cliente.setNome("Cliente");
		cliente.setSobrenome("Test");
		cliente.setDataNascimento(LocalDate.of(1984, 03, 03));
		cliente.setEmail("cliente@example.com");
		
		assertThrows(TransactionSystemException.class, () -> { clienteService.salvar(cliente); });
	}
	
	@Test
	public void deveFalharAoCadastrarUmClienteComCpfExistente() {
		Cliente cliente = new Cliente();
		cliente.setCpf("096.330.110-19");
		cliente.setNome("Cliente");
		cliente.setSobrenome("Test");
		cliente.setDataNascimento(LocalDate.of(1984, 03, 03));
		cliente.setEmail("cliente@example.com");
		
		cliente = clienteService.salvar(cliente);
		
		Cliente repetido = new Cliente();		
		repetido.setCpf("096.330.110-19");
		repetido.setNome("Cliente 1");
		repetido.setSobrenome("Test 1");
		repetido.setDataNascimento(LocalDate.of(1984, 03, 8));
		repetido.setEmail("cliente1@example.com");
		
		assertThrows(EntidadeExistenteException.class, () -> { clienteService.salvar(repetido); });
	}
	
	@Test
	public void deveFalharAoCadastrarUmClienteComCpfInvalido() {
		Cliente cliente = new Cliente();
		cliente.setCpf("096.330.110-18");
		cliente.setNome("Cliente");
		cliente.setSobrenome("Test");
		cliente.setDataNascimento(LocalDate.of(1984, 03, 03));
		cliente.setEmail("cliente@example.com");		
		
		assertThrows(TransactionSystemException.class, () -> { clienteService.salvar(cliente); });
	}
	
	@Test
	public void deveFalharAoCadastrarUmClienteComCpfNoFormatoInvalido() {
		Cliente cliente = new Cliente();
		cliente.setCpf("09633011019");
		cliente.setNome("Cliente");
		cliente.setSobrenome("Test");
		cliente.setDataNascimento(LocalDate.of(1984, 03, 03));
		cliente.setEmail("cliente@example.com");		
		
		assertThrows(CpfFormatoInvalidoException.class, () -> { clienteService.salvar(cliente); });
	}
	
	@Test
	public void deveFalharAoCadastrarUmClienteSemNome() {
		Cliente cliente = new Cliente();
		cliente.setCpf("096.330.110-19");
		cliente.setSobrenome("Test");
		cliente.setDataNascimento(LocalDate.of(1984, 03, 03));
		cliente.setEmail("cliente@example.com");		
		
		assertThrows(TransactionSystemException.class, () -> { clienteService.salvar(cliente); });
	}		

	@Test
	public void deveFalharAoCadastrarUmClienteSemSobrenome() {
		Cliente cliente = new Cliente();
		cliente.setCpf("096.330.110-19");
		cliente.setNome("Cliente");
		cliente.setDataNascimento(LocalDate.of(1984, 03, 03));
		cliente.setEmail("cliente@example.com");		
		
		assertThrows(TransactionSystemException.class, () -> { clienteService.salvar(cliente); });
	}
	
	@Test
	public void deveFalharAoCadastrarUmClienteSemDataDeNascimento() {
		Cliente cliente = new Cliente();
		cliente.setCpf("096.330.110-19");
		cliente.setNome("Cliente");
		cliente.setSobrenome("Test");
		cliente.setEmail("cliente@example.com");		
		
		assertThrows(NegocioException.class, () -> { clienteService.salvar(cliente); });
	}	
	
	@Test
	public void devePassarAoCalcularIdadeMaiorQue18Anos() {
		Cliente cliente = new Cliente();
		cliente.setDataNascimento(LocalDate.of(1984, 03, 03));		
		
		assertTrue(cliente.isMaiorQueDezoitoAnos());
	}	
	
	@Test
	public void deveFalharAoCalcularIdadeMenorQue18Anos() {
		Cliente cliente = new Cliente();
		cliente.setDataNascimento(LocalDate.of(2015, 8, 29));		
		
		assertFalse(cliente.isMaiorQueDezoitoAnos());
	}	
	
	@Test
	public void deveFalaharAoCadastrarUmClienteComEmailExistente() {
		Cliente cliente = new Cliente();
		cliente.setCpf("096.330.110-19");
		cliente.setNome("Cliente");
		cliente.setSobrenome("Test");
		cliente.setDataNascimento(LocalDate.of(1984, 03, 03));
		cliente.setEmail("cliente@example.com");
		
		cliente = clienteService.salvar(cliente);
		
		Cliente repetido = new Cliente();		
		repetido.setCpf("815.698.750-06");
		repetido.setNome("Cliente 1");
		repetido.setSobrenome("Test 1");
		repetido.setDataNascimento(LocalDate.of(1984, 03, 8));
		repetido.setEmail("cliente@example.com");
		
		assertThrows(EntidadeExistenteException.class, () -> { clienteService.salvar(repetido); });	
	}
	
	
	@Test
	public void deveAtualizarUmCliente() {
		Cliente cliente = new Cliente();
		cliente.setCpf("096.330.110-19");
		cliente.setNome("Cliente");
		cliente.setSobrenome("Test");
		cliente.setDataNascimento(LocalDate.of(1984, 03, 03));
		cliente.setEmail("cliente@example.com");
		
		cliente = clienteService.salvar(cliente);
		
		Cliente atual = clienteService.buscarOuFalhar(cliente.getId());
		String nome = "Atualizado";
		atual.setNome(nome);
		
		clienteService.salvar(atual);
		
		assertEquals(nome, atual.getNome());
	}
	
	@Test
	public void deveFalharAoAtualizarUmClienteComCpfExistente() {
		Cliente cliente1 = new Cliente();
		cliente1.setCpf("096.330.110-19");
		cliente1.setNome("Cliente 1");
		cliente1.setSobrenome("Test 1");
		cliente1.setDataNascimento(LocalDate.of(1984, 03, 03));
		cliente1.setEmail("cliente1@example.com");
		
		Cliente cliente2 = new Cliente();
		cliente2.setCpf("266.766.200-81");
		cliente2.setNome("Cliente 2");
		cliente2.setSobrenome("Test 2");
		cliente2.setDataNascimento(LocalDate.of(1984, 03, 03));
		cliente2.setEmail("cliente2@example.com");		
		
		cliente1 = clienteService.salvar(cliente1);
		cliente2 = clienteService.salvar(cliente2);		
		
		cliente2.setCpf("096.330.110-19");
		
		final Cliente clienteAlterado = cliente2;
		
		assertThrows(EntidadeExistenteException.class, () -> { clienteService.salvar(clienteAlterado); });
		
		
	}	
	
	@Test
	public void deveFalharAoAtualizarUmClienteComEmailExistente() {
		Cliente cliente1 = new Cliente();
		cliente1.setCpf("096.330.110-19");
		cliente1.setNome("Cliente 1");
		cliente1.setSobrenome("Test 1");
		cliente1.setDataNascimento(LocalDate.of(1984, 03, 03));
		cliente1.setEmail("cliente1@example.com");
		
		Cliente cliente2 = new Cliente();
		cliente2.setCpf("266.766.200-81");
		cliente2.setNome("Cliente 2");
		cliente2.setSobrenome("Test 2");
		cliente2.setDataNascimento(LocalDate.of(1984, 03, 03));
		cliente2.setEmail("cliente2@example.com");		
		
		cliente1 = clienteService.salvar(cliente1);
		cliente2 = clienteService.salvar(cliente2);		
		
		cliente2.setEmail("cliente1@example.com");
		
		final Cliente clienteAlterado = cliente2;
		
		assertThrows(EntidadeExistenteException.class, () -> { clienteService.salvar(clienteAlterado); });
		
		
	}	
	
}
