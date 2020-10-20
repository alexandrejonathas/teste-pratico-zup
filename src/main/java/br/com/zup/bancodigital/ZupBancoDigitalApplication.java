package br.com.zup.bancodigital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import br.com.zup.bancodigital.infrastructure.repository.CustomJpaRepositoryImpl;

@EnableAsync
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class ZupBancoDigitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZupBancoDigitalApplication.class, args);
	}

}
