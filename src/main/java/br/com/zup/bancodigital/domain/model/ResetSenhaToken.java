package br.com.zup.bancodigital.domain.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ResetSenhaToken {

	
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "conta_id")
	private Long id;
	
	@MapsId
	@OneToOne(fetch = FetchType.EAGER)
	private Conta conta;
	
	private String token;
    
    private LocalDateTime dataExpiracao;
    
    private boolean usado;
    
    public boolean hasExpired() {
    	LocalDateTime agora = LocalDateTime.now();
    	return dataExpiracao.isBefore(agora);
    }
    
}
