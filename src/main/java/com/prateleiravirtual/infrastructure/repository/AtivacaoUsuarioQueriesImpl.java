package com.prateleiravirtual.infrastructure.repository;

import com.prateleiravirtual.domain.model.AtivacaoUsuario;
import com.prateleiravirtual.domain.repository.AtivacaoUsuarioQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * Classe que implementa as queries específicas da classe de AtivacaoUsuario,
 * agregada à classe Usuario (Aggregate Root).
 *
 * @author Jhansen Barreto
 */
@Repository
public class AtivacaoUsuarioQueriesImpl implements AtivacaoUsuarioQueries {

    @PersistenceContext
    private EntityManager manager;

    /**
     * Método para salvar um objeto do tipo AtivacaoUsuario
     *
     * @param ativacao (Objeto a ser salvo)
     * @return -> Objeto após salvo
     */
    @Override
    public AtivacaoUsuario save(AtivacaoUsuario ativacao) {
        return manager.merge(ativacao);
    }
}
