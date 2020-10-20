package br.com.zup.bancodigital.api.v1.assembler;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.zup.bancodigital.api.helper.ResourceUriHelper;
import br.com.zup.bancodigital.api.v1.model.EnderecoModel;
import br.com.zup.bancodigital.api.v1.model.PropostaModel;
import br.com.zup.bancodigital.domain.model.Cliente;
import br.com.zup.bancodigital.domain.model.Proposta;

@Component
public class PropostaModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private FotoCpfModelAssembler fotoCpfModelAssembler;
	
	public PropostaModel toModel(Proposta proposta) {
		Cliente cliente = proposta.getCliente();
		
		PropostaModel propostaModel = modelMapper.map(proposta, PropostaModel.class);
		
		propostaModel.setEndereco(modelMapper.map(cliente.getEndereco(), EnderecoModel.class));
				
		propostaModel.setFotoCpf(fotoCpfModelAssembler.toModel(cliente.getFotoCpf()));
		
		propostaModel.setStatus(proposta.getStatus().getDescricao());
		
		Map<String, String> links = new HashMap<>();
		links.put("aceitar", ResourceUriHelper.createUri("/v1/propostas/"+proposta.getId()+"/aceite"));
		links.put("recusar", ResourceUriHelper.createUri("/v1/propostas/"+proposta.getId()+"/recusa"));
		propostaModel.setLinks(links);		
		
		
		return propostaModel;
	}
	
}
