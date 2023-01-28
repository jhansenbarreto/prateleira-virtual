package com.prateleiravirtual.infrastructure.exception;

/**
 * Classe de exceção específica do contexto de infraestrutura do sistema.
 * Responsável por exceções relacionadas ao armazenamento de arquivos.
 *
 * @author Jhansen Barreto
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
