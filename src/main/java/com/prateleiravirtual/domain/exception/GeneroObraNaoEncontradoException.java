package com.prateleiravirtual.domain.exception;

/**
 * Classe de exceção específica para um gênero de obra não encontrado em uma
 * determinada busca.
 *
 * @author Jhansen Barreto
 */
public class GeneroObraNaoEncontradoException extends EntidadeNaoEncontradaException {

    public GeneroObraNaoEncontradoException(String msg) {
        super(msg);
    }

    public GeneroObraNaoEncontradoException(Long id) {
        this(String.format("Não existe um Gênero com id: %d", id));
    }
}
