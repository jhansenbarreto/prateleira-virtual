package com.prateleiravirtual.domain.exception;

/**
 * Classe de exceção específica para uma obra não encontrada em uma determinada
 * busca.
 *
 * @author Jhansen Barreto
 */
public class ObraNaoEncontradaException extends EntidadeNaoEncontradaException {

    public ObraNaoEncontradaException(String msg) {
        super(msg);
    }

    public ObraNaoEncontradaException(Long id) {
        this(String.format("Não existe uma Obra com id: %d", id));
    }
}
