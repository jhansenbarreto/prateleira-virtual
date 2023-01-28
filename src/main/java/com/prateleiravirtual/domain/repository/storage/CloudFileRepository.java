package com.prateleiravirtual.domain.repository.storage;

import com.prateleiravirtual.domain.model.storage.CloudFile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório da classe que representa um arquivo salvo em nuvem.
 *
 * @author Jhansen Barreto
 */
@Repository
public interface CloudFileRepository extends JpaRepository<CloudFile, String> {

    /**
     * Método que retorna a representação de um arquivo salvo em nuvem, buscando
     * pelo nome.
     *
     * @param name (nome do arquivo)
     * @return -> Optional com um CloudFile ou vazio se não encontrar
     */
    Optional<CloudFile> findByName(String name);
}
