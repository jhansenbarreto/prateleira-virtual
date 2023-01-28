package com.prateleiravirtual.domain.repository.storage;

import com.prateleiravirtual.domain.model.storage.CloudFolder;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório da classe que representa uma pasta salva na nuvem.
 *
 * @author Jhansen Barreto
 */
@Repository
public interface CloudFolderRepository extends JpaRepository<CloudFolder, String> {

    /**
     * Método que retorna a representação de uma pasta salva em nuvem, buscando
     * pelo nome.
     *
     * @param name (nome da pasta)
     * @return -> Optional com um CloudFolder ou vazio se não encontrar
     */
    Optional<CloudFolder> findByName(String name);
}
