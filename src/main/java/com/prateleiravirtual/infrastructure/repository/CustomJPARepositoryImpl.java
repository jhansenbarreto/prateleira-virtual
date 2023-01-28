package com.prateleiravirtual.infrastructure.repository;

import com.prateleiravirtual.domain.repository.CustomJPARepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * Classe que implementa a interface CustomJPARepository, sobrescrevendo métodos
 * necessários para serem utilizados em qualquer outro repositório que precise.
 *
 * @author Jhansen Barreto
 * @param <T> (Entidade genérica)
 * @param <ID> (Tipo do Identificador da Entidade)
 */
public class CustomJPARepositoryImpl<T, ID>
        extends SimpleJpaRepository<T, ID> implements CustomJPARepository<T, ID> {

    private final EntityManager manager;

    public CustomJPARepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.manager = entityManager;
    }

    /**
     * Método para desacoplar um objeto do contexto de persistência do JPA.
     * Deixar de ser gerenciado momentaneamente.
     *
     * @param entity (Entidade a ser desacoplada)
     */
    @Override
    public void detach(T entity) {
        manager.detach(entity);
    }
}
