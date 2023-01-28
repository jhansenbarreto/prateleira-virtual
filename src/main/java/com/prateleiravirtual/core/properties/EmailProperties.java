package com.prateleiravirtual.core.properties;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Classe de propriedades relacionadas ao envio de e-mails.
 *
 * @author Jhansen Barreto
 */
@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("prateleira-virtual.email")
public class EmailProperties {

    @NotNull
    private String remetente;

    @NotNull
    private Implementacao impl = Implementacao.FAKE;

    /**
     * Enumeração criada para definir quais opções estão disponíveis para o
     * envio de e-mails: SMTP (e-mail real) ou FAKE (simulação de envio).
     */
    public enum Implementacao {
        SMTP, FAKE;
    }
}
