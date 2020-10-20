package br.com.zup.bancodigital.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.zup.bancodigital.domain.service.EnvioEmailService;
import br.com.zup.bancodigital.infrastructure.email.FakeEnvioEmailService;
import br.com.zup.bancodigital.infrastructure.email.SandboxEnvioEmailService;
import br.com.zup.bancodigital.infrastructure.email.SmtpEnvioEmailService;

@Configuration
public class MailConfig {
    
	@Autowired
    private MailProperties mailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
        // Acho melhor usar switch aqui do que if/else if
        switch (mailProperties.getImpl()) {
            case FAKE:
                return new FakeEnvioEmailService();
            case SMTP:
                return new SmtpEnvioEmailService();
            case SANDBOX:
                return new SandboxEnvioEmailService();                
            default:
                return null;
        }
    }
}
