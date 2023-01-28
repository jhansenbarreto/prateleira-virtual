package com.prateleiravirtual.infrastructure.exception;

/**
 * Classe de exceção específica do contexto de infraestrutura do sistema.
 * Responsável por exceções relacionadas ao envio de e-mails.
 *
 * @author Jhansen Barreto
 */
public class EnvioEmailException extends RuntimeException {

    public EnvioEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
