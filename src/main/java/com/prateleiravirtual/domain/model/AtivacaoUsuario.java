package com.prateleiravirtual.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Classe que representa o meio pelo qual um usuário será ativado.
 *
 * @author Jhansen Barreto
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class AtivacaoUsuario {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "usuario_id")
    private Long id;

    @Column(nullable = false, name = "codigo_ativacao", length = 9)
    private String codigo;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCriacao;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Usuario usuario;

    public AtivacaoUsuario() {
    }

    public AtivacaoUsuario(String codigo, Usuario usuario) {
        this.codigo = codigo;
        this.usuario = usuario;
    }
}
