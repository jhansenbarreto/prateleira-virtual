package com.prateleiravirtual.infrastructure.repository;

import com.prateleiravirtual.domain.model.RedefinicaoSenha;
import com.prateleiravirtual.domain.repository.RedefinicaoSenhaQueries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * Classe que implementa as queries específicas da classe de RedefinicaoSenha,
 * agregada à classe Usuario (Aggregate Root).
 *
 * @author Jhansen Barreto
 */
@Repository
public class RedefinicaoSenhaQueriesImpl implements RedefinicaoSenhaQueries {

    @PersistenceContext
    private EntityManager manager;

    /**
     * Método para salvar um objeto do tipo RedefinicaoSenha
     *
     * @param redefinicao (Objeto a ser salvo)
     * @return -> Objeto após salvo
     */
    @Override
    public RedefinicaoSenha save(RedefinicaoSenha redefinicao) {
        return manager.merge(redefinicao);
    }
}
