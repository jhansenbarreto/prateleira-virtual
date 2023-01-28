package com.prateleiravirtual.domain.model.storage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classe restrita do modelo de armazenamento em nuvem, representando uma pasta
 * criada no serviço da Box.
 *
 * @author Jhansen Barreto
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class CloudFolder {

    @EqualsAndHashCode.Include
    @Id
    private String id;

    @Column(name = "name_folder", nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "folder")
    private List<CloudFile> files;

    public CloudFolder() {
    }

    public CloudFolder(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
