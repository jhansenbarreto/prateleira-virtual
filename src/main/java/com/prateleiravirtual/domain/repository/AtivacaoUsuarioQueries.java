package com.prateleiravirtual.domain.repository;

import com.prateleiravirtual.domain.model.AtivacaoUsuario;

/**
 * Interface que define os métodos de repositório da classe que representa os
 * meios para uma ativação de usuário. Ver implementações.
 *
 * @author Jhansen Barreto
 */
public interface AtivacaoUsuarioQueries {

    /**
     * Método para salvar uma AtivacaoUsuario. Ver implementações.
     *
     * @param ativacao (AtivacaoUsuario a ser salva)
     * @return -> AtivacaoUsuario após salva
     */
    AtivacaoUsuario save(AtivacaoUsuario ativacao);
}
