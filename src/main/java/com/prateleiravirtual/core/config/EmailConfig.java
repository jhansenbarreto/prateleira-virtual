package com.prateleiravirtual.core.config;

import com.prateleiravirtual.core.properties.EmailProperties;
import com.prateleiravirtual.domain.service.EmailService;

import com.prateleiravirtual.infrastructure.email.EnvioEmailFAKEImpl;
import com.prateleiravirtual.infrastructure.email.EnvioEmailSMTPImpl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe destinada a configurações relacionadas ao envio de e-mails.
 *
 * @author Jhansen Barreto
 */
@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    /**
     * Método para inserir um Bean no contexto do Spring e permitir a injeção de
     * dependência para uma instância de EmailService.
     *
     * @return -> Instância de EmailService de acordo com a implementação
     * escolhida no 'application.properties'
     */
    @Bean
    public EmailService envioEmailService() {
        return switch (emailProperties.getImpl()) {
            case FAKE ->
                new EnvioEmailFAKEImpl();
            case SMTP ->
                new EnvioEmailSMTPImpl();
            default ->
                null;
        };
    }
}
