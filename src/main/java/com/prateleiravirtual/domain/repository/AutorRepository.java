package com.prateleiravirtual.domain.repository;

import com.prateleiravirtual.domain.model.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositório da classe que representa um autor e suas classes agregadas.
 *
 * @author Jhansen Barreto
 */
@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    /**
     * Método que retorna uma lista de Autor, pesquisados por nome.
     *
     * @param search (parâmetro de pesquisa)
     * @return -> Lista de Autor
     */
    List<Autor> findByNomeContaining(String search);

    /**
     * Método para deletar a Imagem de um Autor.
     *
     * @param autorId (Identificador do Autor)
     */
    @Modifying
    @Query("DELETE FROM ImagemAutor img WHERE img.id = :autorId")
    void deleteImagem(@Param("autorId") Long autorId);
}
