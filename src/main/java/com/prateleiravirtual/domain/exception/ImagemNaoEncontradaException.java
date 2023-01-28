package com.prateleiravirtual.domain.exception;

import lombok.Getter;

/**
 * Classe de exceção específica para uma imagem não encontrada em uma
 * determinada busca.
 *
 * @author Jhansen Barreto
 */
public class ImagemNaoEncontradaException extends EntidadeNaoEncontradaException {

    public ImagemNaoEncontradaException(String msg) {
        super(msg);
    }

    public ImagemNaoEncontradaException(Long id, TipoImagem t) {
        this(String.format("%s com id '%d' não tem uma imagem associada.", t.getDescricao(), id));
    }

    /**
     * Enumeração criada para facilitar a customização da mensagem de erro da
     * exceção que pode ser lançada para um dos tipos listados.
     */
    @Getter
    public enum TipoImagem {
        AUTOR("O Autor"),
        OBRA("A Obra"),
        USUARIO("O Usuário");

        private final String descricao;

        TipoImagem(String descricao) {
            this.descricao = descricao;
        }
    }
}
