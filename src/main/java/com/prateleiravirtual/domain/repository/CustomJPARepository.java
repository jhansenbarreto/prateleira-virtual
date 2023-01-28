package com.prateleiravirtual.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Interface que define métodos que possam ser utilizados por quaisquer
 * repositórios que herdem dessa classe. Ver implementações.
 *
 * @author Jhansen Barreto
 * @param <T> (Entidade genérica)
 * @param <ID> (Tipo do Identificador da Entidade)
 */
@NoRepositoryBean
public interface CustomJPARepository<T, ID> extends JpaRepository<T, ID> {

    /**
     * Método para desacoplar um objeto do contexto de persistência do JPA.
     * Deixar de ser gerenciado momentaneamente. Ver implementações.
     *
     * @param entity (Entidade a ser desacoplada)
     */
    public void detach(T entity);
}
