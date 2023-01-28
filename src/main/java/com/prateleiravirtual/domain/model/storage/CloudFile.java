package com.prateleiravirtual.domain.model.storage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classe restrita do modelo de armazenamento em nuvem, representando um arquivo
 * já salvo no serviço da Box.
 *
 * @author Jhansen Barreto
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class CloudFile {

    @EqualsAndHashCode.Include
    @Id
    private String id;

    @Column(name = "name_file", nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "cloud_folder_id", nullable = false)
    private CloudFolder folder;

    @Column(nullable = false, length = 255)
    private String url;

    public CloudFile() {
    }
}
