package com.prateleiravirtual.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classe que representa imagens de autores.
 *
 * @author Jhansen Barreto
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ImagemAutor {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "autor_id")
    private Long id;

    @Embedded
    private ImagemDetalhes detalhes;
    
    @Override
    public String toString() {
        return detalhes.getUrl();
    }
}
