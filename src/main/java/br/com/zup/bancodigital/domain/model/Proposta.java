package br.com.zup.bancodigital.domain.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.AbstractAggregateRoot;

import br.com.zup.bancodigital.domain.event.PropostaAceitaEvent;
import br.com.zup.bancodigital.domain.event.PropostaRecusadaEvent;
import br.com.zup.bancodigital.domain.exception.NegocioException;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class Proposta extends AbstractAggregateRoot<Proposta> {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;	
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Cliente cliente;
	
	private Boolean aceite;
	
	@Enumerated(EnumType.STRING)
	private StatusProposta status = StatusProposta.CRIADA;
	
	@OneToOne(mappedBy = "proposta")
	private Conta conta;
	
	private void setStatus(StatusProposta novoStatus) {
		if(getStatus().naoPodeAlterarPara(novoStatus)) {
			throw new NegocioException(
					String.format("Status da proposta %s não pode ser alterado de %s para %s", 
							getId(), getStatus().getDescricao(), novoStatus.getDescricao()));
		}
		this.status = novoStatus;
	}	
	
	public boolean isNova() {
		return getId() == null;
	}
	
	public boolean isLiberada() {
		return StatusProposta.LIBERADA.equals(getStatus());
	}
	
	public void aceitar() {
		
		if(!StatusProposta.LIBERADA.equals(getStatus())) {
			throw new NegocioException("A proposta deve estar liberada para ser aceita!");
		}
		
		setAceite(true);
		registerEvent(new PropostaAceitaEvent(this));
	}
	
	public void recusar() {
		setAceite(false);
		registerEvent(new PropostaRecusadaEvent(this));
	}
	
	public void liberar() {
		setStatus(StatusProposta.LIBERADA);
	}		
			
	
}
