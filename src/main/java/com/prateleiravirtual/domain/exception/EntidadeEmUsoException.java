package com.prateleiravirtual.domain.exception;

/**
 * Classe de exceção específica para qualquer entidade em uso que não pode ser
 * deletada.
 *
 * @author Jhansen Barreto
 */
public class EntidadeEmUsoException extends RuntimeException {

    public EntidadeEmUsoException(String msg) {
        super(msg);
    }
}
