package com.prateleiravirtual.domain.exception;

/**
 * Classe de exceção específica para um tipo de obra não encontrado em uma
 * determinada busca.
 *
 * @author Jhansen Barreto
 */
public class TipoObraNaoEncontradoException extends EntidadeNaoEncontradaException {

    public TipoObraNaoEncontradoException(String msg) {
        super(msg);
    }

    public TipoObraNaoEncontradoException(Long id) {
        this(String.format("Não existe um Tipo com id: %d", id));
    }
}
