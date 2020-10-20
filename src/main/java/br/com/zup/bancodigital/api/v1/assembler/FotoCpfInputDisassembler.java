package br.com.zup.bancodigital.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.zup.bancodigital.api.v1.model.input.FotoCpfInput;
import br.com.zup.bancodigital.domain.model.FotoCpf;

@Component
public class FotoCpfInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public FotoCpf toDomainObject(FotoCpfInput fotoCpfInput) {
		return modelMapper.map(fotoCpfInput, FotoCpf.class);
	}

	public void copyToDomainObject(FotoCpfInput fotoCpfInput, FotoCpf fotoCpf) {
		modelMapper.map(fotoCpfInput, fotoCpf);
	}
}
