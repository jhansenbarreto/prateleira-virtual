package com.prateleiravirtual.infrastructure.exception;

/**
 * Classe de exceção específica do contexto de infraestrutura do sistema.
 * Responsável por exceções relacionadas ao serviço de armazenamento em nuvem.
 *
 * @author Jhansen Barreto
 */
public class CloudServiceException extends RuntimeException {

    public CloudServiceException(String message) {
        super(message);
    }

    public CloudServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
