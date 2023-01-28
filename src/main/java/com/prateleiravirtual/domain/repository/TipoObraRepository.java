package com.prateleiravirtual.domain.repository;

import com.prateleiravirtual.domain.model.TipoObra;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório da classe que representa um tipo de obra.
 *
 * @author Jhansen Barreto
 */
@Repository
public interface TipoObraRepository extends JpaRepository<TipoObra, Long> {

    /**
     * Método que retorna uma lista de TipoObra, pesquisados por descrição.
     *
     * @param search (parâmetro de pesquisa)
     * @return -> Lista de TipoObra
     */
    List<TipoObra> findByDescricaoContaining(String search);
}
