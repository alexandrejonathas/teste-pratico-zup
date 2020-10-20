package br.com.zup.bancodigital.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import br.com.zup.bancodigital.domain.exception.NegocioException;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FotoCpf {

	@EqualsAndHashCode.Include
	@Id
	@Column(name = "cliente_id")
	private Long id;	
	
	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	private Cliente cliente;
	
	@NotBlank
	private String nome;
	
	private String contentType;
	
	private Long tamanho;
	
	@Enumerated(EnumType.STRING)
	private StatusFotoCpf status = StatusFotoCpf.CRIADO;
	
	private int tentativaAceite = 0;
	
	public boolean isNovo() {
		return getId() == null;
	}
	
	public void aceite() {
		setStatus(StatusFotoCpf.ACEITO);
		atualizarTentativas();
	}
	
	public void desistir() {
		
		if(!StatusFotoCpf.CRIADO.equals(getStatus())) {
			throw new NegocioException(String.format("Só é possivel deistir de validar o cpf se o status for igual a %s", StatusFotoCpf.CRIADO));
		}
		
		if(tentativaAceite < 2) {
			throw new NegocioException("Não é possivel desistir com menos de duas tentativas!");
		}
		
		setStatus(StatusFotoCpf.CANCELADO);
	}
	
	public void atualizarTentativas() {
		tentativaAceite += 1;		
	}
	
}
