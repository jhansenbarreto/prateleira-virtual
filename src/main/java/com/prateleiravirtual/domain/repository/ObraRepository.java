package com.prateleiravirtual.domain.repository;

import com.prateleiravirtual.domain.model.Obra;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositório da classe que representa uma obra e suas classes agregadas.
 *
 * @author Jhansen Barreto
 */
@Repository
public interface ObraRepository extends JpaRepository<Obra, Long> {

    /**
     * Método que retorna uma lista de Obra, pesquisadas por título ou
     * subtítulo.
     *
     * @param search (parâmetro de pesquisa)
     * @return -> Lista de Obra
     */
    @Query("FROM Obra o WHERE o.titulo LIKE %:search% OR o.subtitulo LIKE %:search%")
    List<Obra> findByTituloOrSubtitulo(String search);

    /**
     * Método para deletar a Imagem de capa de uma Obra.
     *
     * @param obraId (Identificador da Obra)
     */
    @Modifying
    @Query("DELETE FROM ImagemObra img WHERE img.id = :obraId")
    void deleteImagemCapa(@Param("obraId") Long obraId);
}
