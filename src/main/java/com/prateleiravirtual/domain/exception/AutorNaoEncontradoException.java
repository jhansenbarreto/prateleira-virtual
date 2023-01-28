package com.prateleiravirtual.domain.exception;

/**
 * Classe de exceção específica para um autor não encontrado em uma determinada
 * busca.
 *
 * @author Jhansen Barreto
 */
public class AutorNaoEncontradoException extends EntidadeNaoEncontradaException {

    public AutorNaoEncontradoException(String msg) {
        super(msg);
    }

    public AutorNaoEncontradoException(Long id) {
        this(String.format("Não existe um Autor com id: %d", id));
    }
}
