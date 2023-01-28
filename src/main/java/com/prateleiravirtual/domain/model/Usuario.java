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
import jakarta.persistence.OneToOne;

import java.time.OffsetDateTime;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Classe que representa usuários.
 *
 * @author Jhansen Barreto
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Usuario {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String username;

    @Column(length = 255, nullable = false)
    private String email;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String senha;
    
    @Column(nullable = false)
    private Boolean ativo = Boolean.FALSE;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "imagem_usuario_id")
    private ImagemUsuario imagem;
    
    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCriacao;
    
    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @ManyToMany
    @JoinTable(name = "leitura_feita",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "obra_id"))
    private Set<Obra> obrasLidas;

    @ManyToMany
    @JoinTable(name = "leitura_a_fazer",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "obra_id"))
    private Set<Obra> obrasParaLer;

    @ManyToMany
    @JoinTable(name = "leitura_em_andamento",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "obra_id"))
    private Set<Obra> obrasEmLeitura;
}
