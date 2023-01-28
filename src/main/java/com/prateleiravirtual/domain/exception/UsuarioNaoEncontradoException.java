package com.prateleiravirtual.domain.exception;

/**
 * Classe de exceção específica para um usuário não encontrado em uma
 * determinada busca.
 *
 * @author Jhansen Barreto
 */
public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    public UsuarioNaoEncontradoException(String msg) {
        super(msg);
    }

    public UsuarioNaoEncontradoException(Long id) {
        this(String.format("Não existe um Usuario com id: %d", id));
    }
}
