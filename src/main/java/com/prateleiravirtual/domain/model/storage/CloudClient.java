package com.prateleiravirtual.domain.model.storage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classe restrita do modelo de armazenamento em nuvem, representando um cliente
 * para uso da API Box. As informações são restritas e criptografadas. Os bytes
 * do resultado após a criptografia são salvos no banco de dados.
 *
 * @author Jhansen Barreto
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class CloudClient {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "MEDIUMBLOB")
    private byte[] bytes;
}
