package com.prateleiravirtual.core.config;

import com.prateleiravirtual.core.properties.StorageProperties;
import com.prateleiravirtual.domain.service.ImagemStorageService;

import com.prateleiravirtual.infrastructure.storage.ImagemCloudStorageImpl;
import com.prateleiravirtual.infrastructure.storage.ImagemLocalStorageImpl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe destinada a configurações relacionadas ao armazenamento de arquivos.
 *
 * @author Jhansen Barreto
 */
@Configuration
public class StorageConfig {

    @Autowired
    private StorageProperties storageProperties;

    /**
     * Método para inserir um Bean no contexto do Spring e permitir a injeção de
     * dependência para uma instância de ImagemStorageService.
     *
     * @return -> Instância de ImagemStorageService de acordo com a
     * implementação escolhida no 'application.properties'
     */
    @Bean
    public ImagemStorageService imagemStorageService() {
        return switch (storageProperties.getImpl()) {
            case LOCAL ->
                new ImagemLocalStorageImpl();
            case CLOUD ->
                new ImagemCloudStorageImpl();
            default ->
                null;
        };
    }
}
