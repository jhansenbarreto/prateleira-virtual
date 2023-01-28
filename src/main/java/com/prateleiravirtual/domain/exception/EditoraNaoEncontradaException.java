package com.prateleiravirtual.domain.exception;

/**
 * Classe de exceção específica para uma editora não encontrada em uma
 * determinada busca.
 *
 * @author Jhansen Barreto
 */
public class EditoraNaoEncontradaException extends EntidadeNaoEncontradaException {

    public EditoraNaoEncontradaException(String msg) {
        super(msg);
    }

    public EditoraNaoEncontradaException(Long id) {
        this(String.format("Não existe uma Editora com id: %d", id));
    }
}
