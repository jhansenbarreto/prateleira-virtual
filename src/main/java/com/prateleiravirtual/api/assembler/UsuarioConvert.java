package com.prateleiravirtual.api.assembler;

import com.prateleiravirtual.api.model.dto.input.UsuarioInput;
import com.prateleiravirtual.api.model.dto.input.UsuarioUpdateInput;
import com.prateleiravirtual.api.model.dto.output.UsuarioOutput;
import com.prateleiravirtual.domain.model.Usuario;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe destinada a conversão de objetos. Específica para conversão entre os
 * tipos: Usuario (modelo de domínio), UsuarioInput e UsuarioUpdateInput
 * (modelos de entrada) e UsuarioOutput (modelo de saída). Os modelos de entrada
 * e saída foram implementados seguindo o padrão de projetos DTO (Data Transfer
 * Object).
 *
 * @author Jhansen Barreto
 */
@Component
public class UsuarioConvert {

    @Autowired
    private ModelMapper mapper;

    /**
     * Este método recebe um objeto do modelo do domínio e converte-o para o
     * modelo de saída.
     *
     * @param objectModel (Objeto do modelo de domínio)
     * @return -> Objeto do modelo de saída
     */
    public UsuarioOutput toOutput(Usuario objectModel) {
        return mapper.map(objectModel, UsuarioOutput.class);
    }

    /**
     * Este método recebe um objeto do modelo de entrada e converte-o para o
     * modelo de domínio.
     *
     * @param input (Objeto do modelo de entrada)
     * @return -> Objeto do modelo de domínio
     */
    public Usuario toObjectModel(UsuarioInput input) {
        return mapper.map(input, Usuario.class);
    }

    /**
     * Este método recebe uma coleção com objetos do tipo do modelo de domínio e
     * os converte para o modelo de saída, servindo uma lista.
     *
     * @param collection (Coleção do modelo de domínio)
     * @return -> Lista do modelo de saída
     */
    public List<UsuarioOutput> toListOutput(Collection<Usuario> collection) {
        return collection.stream().map(object -> toOutput(object)).collect(Collectors.toList());
    }

    /**
     * Este método copia os valores do objeto do modelo de entrada para o objeto
     * do modelo de domínio.
     *
     * @param input (Objeto do modelo de entrada)
     * @param objectModel (Objeto do modelo de domínio)
     */
    public void copyToObjectModel(UsuarioInput input, Usuario objectModel) {
        mapper.map(input, objectModel);
    }

    /**
     * Este método copia os valores do objeto do modelo de entrada que
     * representa uma atualização, para o objeto do modelo de domínio.
     *
     * @param input (Objeto do modelo de entrada que representa uma atualização)
     * @param objectModel (Objeto do modelo de domínio)
     */
    public void copyToObjectModel(UsuarioUpdateInput input, Usuario objectModel) {
        mapper.map(input, objectModel);
    }
}
