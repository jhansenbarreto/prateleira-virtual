package com.prateleiravirtual.infrastructure.email;

import lombok.extern.slf4j.Slf4j;

/**
 * Classe que simula envio de e-mails, imprimindo no console um falso envio.
 *
 * @author Jhansen Barreto
 */
@Slf4j
public class EnvioEmailFAKEImpl extends EnvioEmailSMTPImpl {

    /**
     * Método responsável por imprimir no console o corpo do e-mail, simulando
     * um envio. Método ideal para ambiente de testes.
     *
     * @param email (Objeto com requisitos necessários para um envio)
     */
    @Override
    public void enviar(Email email) {
        log.info("\n\n[E-MAIL FAKE] Para: {}\n{}",
                email.getDestinatarios(),
                getBodyMailTemplate(email.getCorpo(), email.getVariaveis()));
    }
}
