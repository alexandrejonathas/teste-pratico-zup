package br.com.zup.bancodigital.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.zup.bancodigital.api.v1.model.ResetSenhaTokenModel;
import br.com.zup.bancodigital.domain.model.ResetSenhaToken;

@Component
public class ResetSenhaTokenModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ResetSenhaTokenModel toModel(ResetSenhaToken resetSenhaToken) {
		return modelMapper.map(resetSenhaToken, ResetSenhaTokenModel.class);
	}
	
	public List<ResetSenhaTokenModel> toCollection(List<ResetSenhaToken> resetSenhaTokens){
		return resetSenhaTokens.stream()
				.map(token -> toModel(token))
				.collect(Collectors.toList());
	}
	
}
