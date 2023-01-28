package com.prateleiravirtual.domain.repository;

import com.prateleiravirtual.domain.model.Editora;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório da classe que representa uma editora.
 *
 * @author Jhansen Barreto
 */
@Repository
public interface EditoraRepository extends JpaRepository<Editora, Long> {

    /**
     * Método que retorna uma lista de Editora, pesquisadas por nome.
     *
     * @param search (parâmetro de pesquisa)
     * @return -> Lista de Editora
     */
    List<Editora> findByNomeContaining(String search);
}
