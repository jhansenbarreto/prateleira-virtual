package com.prateleiravirtual.core.properties;

import jakarta.validation.constraints.NotNull;
import java.nio.file.Path;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Classe de propriedades relacionadas ao armazenamento de arquivos.
 *
 * @author Jhansen Barreto
 */
@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("prateleira-virtual.storage")
public class StorageProperties {

    private LocalDisk localDisk = new LocalDisk();

    @NotNull
    private Implementacao impl = Implementacao.LOCAL;

    /**
     * Classe criada para definir os caminhos de armazenamento de arquivos em
     * caso de escolha de armazenamento local.
     */
    @Getter
    @Setter
    public class LocalDisk {

        private Path diretorioImagemAutor;
        private Path diretorioImagemObra;
        private Path diretorioImagemUser;
    }

    /**
     * Enumeração criada para definir quais opções estão disponíveis para a
     * escolha do local de armazenamento de arquivos: Disco Local ou Nuvem.
     */
    public enum Implementacao {
        LOCAL, CLOUD;
    }
}
