package br.com.zup.bancodigital.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.zup.bancodigital.api.helper.ResourceUriHelper;
import br.com.zup.bancodigital.api.v1.model.FotoCpfModel;
import br.com.zup.bancodigital.domain.model.FotoCpf;

@Component
public class FotoCpfModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FotoCpfModel toModel(FotoCpf fotoCpf) {
		FotoCpfModel fotoCpfModel = modelMapper.map(fotoCpf, FotoCpfModel.class);
				
		fotoCpfModel.setLink(ResourceUriHelper.createUri("/v1/clientes/"+fotoCpf.getCliente().getId()+"/foto-cpf"));
		
		return fotoCpfModel;
	}
	
	public List<FotoCpfModel> toCollection(List<FotoCpf> fotos){
		return fotos.stream()
				.map(fotoCpf -> toModel(fotoCpf))
				.collect(Collectors.toList());
	}	
	
}
