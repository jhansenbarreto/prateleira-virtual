package com.prateleiravirtual.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classe que representa obras.
 *
 * @author Jhansen Barreto
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Obra {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 50, nullable = false)
    private String titulo;
    
    @Column(length = 50)
    private String subtitulo;
    
    @Column(nullable = false)
    private Integer ano;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;
    
    @ManyToOne
    @JoinColumn(name = "tipo_obra_id", nullable = false)
    private TipoObra tipo;
    
    @ManyToOne
    @JoinColumn(name = "genero_obra_id", nullable = false)
    private GeneroObra genero;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "imagem_obra_id")
    private ImagemObra capa;
    
    @Column
    private Integer paginas;
    
    @ManyToOne
    @JoinColumn(nullable = false, name = "editora_id")
    private Editora editora;
    
    @ManyToMany
    @JoinTable(name = "obra_autor",
            joinColumns = @JoinColumn(name = "obra_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private Set<Autor> autores = new LinkedHashSet<>();
}
