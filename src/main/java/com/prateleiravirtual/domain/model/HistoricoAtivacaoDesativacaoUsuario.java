package com.prateleiravirtual.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.CreationTimestamp;

/**
 * Classe que representa o histórico de ativação e desativação de usuários.
 *
 * @author Jhansen Barreto
 */
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class HistoricoAtivacaoDesativacaoUsuario {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 9)
    private AcaoHistorico acao;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCriacao;
}
