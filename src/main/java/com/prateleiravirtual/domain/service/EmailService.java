package com.prateleiravirtual.domain.service;

import jakarta.validation.constraints.NotEmpty;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

/**
 * Interface responsável por definir os métodos relacionados ao envio de
 * e-mails. Ver implementações.
 *
 * @author Jhansen Barreto
 */
public interface EmailService {

    /**
     * Método de envio de e-mails. Ver implementações.
     *
     * @param email (Objeto com informações necessárias para um envio)
     */
    void enviar(Email email);

    /**
     * Classe que define as informações necessárias para o envio de um e-mail.
     */
    @Getter
    @Builder
    class Email {

        @Singular
        @NotEmpty
        private final Set<String> destinatarios;

        @NonNull
        private final String assunto;

        @NonNull
        private final String corpo;

        @Singular("variavel")
        private Map<String, Object> variaveis;
    }
}
