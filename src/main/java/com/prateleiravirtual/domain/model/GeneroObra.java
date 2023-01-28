package com.prateleiravirtual.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classe que representa gêneros de obras.
 *
 * @author Jhansen Barreto
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class GeneroObra {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "genero")
    private List<Obra> obras;

    @Override
    public String toString() {
        return descricao;
    }
}
