package com.prateleiravirtual.domain.repository.storage;

import com.prateleiravirtual.domain.model.storage.CloudClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório da classe que representa o cliente da API Box.
 *
 * @author Jhansen Barreto
 */
@Repository
public interface CloudClientRepository extends JpaRepository<CloudClient, Long> {
}
