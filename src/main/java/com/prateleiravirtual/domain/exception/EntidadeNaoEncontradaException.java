package com.prateleiravirtual.domain.exception;

/**
 * Classe de exceção genérica para representar qualquer entidade não encontrada
 * em uma determinada busca. Classe abstrata, não pode ser instanciada.
 *
 * @author Jhansen Barreto
 */
public abstract class EntidadeNaoEncontradaException extends RuntimeException {

    public EntidadeNaoEncontradaException(String msg) {
        super(msg);
    }
}
