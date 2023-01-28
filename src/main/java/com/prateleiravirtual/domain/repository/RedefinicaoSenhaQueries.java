package com.prateleiravirtual.domain.repository;

import com.prateleiravirtual.domain.model.RedefinicaoSenha;

/**
 * Interface que define os métodos de repositório da classe que representa os
 * meios para uma redefinição de senha de usuário. Ver implementações.
 *
 * @author Jhansen Barreto
 */
public interface RedefinicaoSenhaQueries {

    /**
     * Método para salvar uma RedefinicaoSenha. Ver implementações.
     *
     * @param redefinicao (RedefinicaoSenha a ser salva)
     * @return -> RedefinicaoSenha após salva
     */
    RedefinicaoSenha save(RedefinicaoSenha redefinicao);
}
