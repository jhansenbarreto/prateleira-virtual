package com.prateleiravirtual.api.exceptionhandler;

import lombok.Getter;

/**
 * Enumeração criada para facilitar a criação de um objeto do tipo Error,
 * existente neste mesmo pacote (api.exceptionhandler), servindo informações já
 * pré definidas.
 *
 * @author Jhansen Barreto
 */
@Getter
public enum ErrorType {

    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    CORPO_NAO_LEGIVEL("/corpo-nao-legivel", "Corpo não legível"),
    PROPRIEDADE_NAO_EXISTE("/propriedade-nao-existe", "Propriedade inexistente"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos");

    private final String uri;
    private final String title;

    ErrorType(String path, String title) {
        this.uri = "https://api.prateleira-virtual.local" + path;
        this.title = title;
    }
}
