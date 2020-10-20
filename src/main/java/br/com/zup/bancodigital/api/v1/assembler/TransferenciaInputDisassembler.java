package br.com.zup.bancodigital.api.v1.assembler;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.zup.bancodigital.api.v1.model.input.ClienteInput;
import br.com.zup.bancodigital.api.v1.model.input.TransferenciaInput;
import br.com.zup.bancodigital.domain.model.Cliente;
import br.com.zup.bancodigital.domain.model.Transferencia;

@Component
public class TransferenciaInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;	

	public Transferencia toDomainObject(TransferenciaInput transferenciaInput) {
		return modelMapper.map(transferenciaInput, Transferencia.class);
	}	
	
	public void copyToDomainObject(ClienteInput clienteInput, Cliente cliente) {
		modelMapper.map(clienteInput, cliente);
	}	
	
	public List<Transferencia> toDomainObjectList(List<TransferenciaInput> transferenciasInput) {
		return modelMapper.map(transferenciasInput, new TypeToken<List<Transferencia>>() {}.getType());
	}
}
