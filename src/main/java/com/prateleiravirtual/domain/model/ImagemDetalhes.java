package com.prateleiravirtual.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Classe que representa detalhes genéricos de todas as imagens do modelo.
 *
 * @author Jhansen Barreto
 */
@Data
@Embeddable
@AllArgsConstructor
public class ImagemDetalhes {

    @Column(nullable = false, length = 255)
    private String nomeArquivo;

    @Column(nullable = false, length = 20)
    private String contentType;

    @Column(nullable = false)
    private Long tamanho;

    @Column(length = 255)
    private String url;

    public ImagemDetalhes() {
    }
}
