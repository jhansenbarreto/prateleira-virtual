package com.prateleiravirtual;

import com.prateleiravirtual.infrastructure.repository.CustomJPARepositoryImpl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Prateleira Virtual - API
 *
 * @author Jhansen Barreto
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJPARepositoryImpl.class)
public class PrateleiraVirtualApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrateleiraVirtualApplication.class, args);
    }

    /**
     * Classe global de constantes para uso no armazenamento de imagens
     */
    public static final class GlobalConstants {

        public static final String AUTOR = "AUTOR";
        public static final String OBRA = "OBRA";
        public static final String USUARIO = "USER";
    }
}
