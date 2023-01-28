package com.prateleiravirtual.domain.repository;

import com.prateleiravirtual.domain.model.GeneroObra;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório da classe que representa um gênero de obra.
 *
 * @author Jhansen Barreto
 */
@Repository
public interface GeneroObraRepository extends JpaRepository<GeneroObra, Long> {

    /**
     * Método que retorna uma lista de GeneroObra, pesquisados por descrição.
     *
     * @param search (parâmetro de pesquisa)
     * @return -> Lista de GeneroObra
     */
    List<GeneroObra> findByDescricaoContaining(String search);
}
