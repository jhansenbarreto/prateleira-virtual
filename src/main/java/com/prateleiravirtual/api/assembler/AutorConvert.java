package com.prateleiravirtual.api.assembler;

import com.prateleiravirtual.api.model.dto.input.AutorInput;
import com.prateleiravirtual.api.model.dto.output.AutorOutput;
import com.prateleiravirtual.domain.model.Autor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe destinada a conversão de objetos. Específica para conversão entre os
 * tipos: Autor (modelo de domínio), AutorInput (modelo de entrada) e
 * AutorOutput (modelo de saída). Os modelos de entrada e saída foram
 * implementados seguindo o padrão de projetos DTO (Data Transfer Object).
 *
 * @author Jhansen Barreto
 */
@Component
public class AutorConvert {

    @Autowired
    private ModelMapper mapper;

    /**
     * Este método recebe um objeto do modelo do domínio e converte-o para o
     * modelo de saída.
     *
     * @param objectModel (Objeto do modelo de domínio)
     * @return -> Objeto do modelo de saída
     */
    public AutorOutput toOutput(Autor objectModel) {
        return mapper.map(objectModel, AutorOutput.class);
    }

    /**
     * Este método recebe um objeto do modelo de entrada e converte-o para o
     * modelo de domínio.
     *
     * @param input (Objeto do modelo de entrada)
     * @return -> Objeto do modelo de domínio
     */
    public Autor toObjectModel(AutorInput input) {
        return mapper.map(input, Autor.class);
    }

    /**
     * Este método recebe uma coleção com objetos do tipo do modelo de domínio e
     * os converte para o modelo de saída, servindo uma lista.
     *
     * @param collection (Coleção do modelo de domínio)
     * @return -> Lista do modelo de saída
     */
    public List<AutorOutput> toListOutput(Collection<Autor> collection) {
        return collection.stream().map(object -> toOutput(object)).collect(Collectors.toList());
    }

    /**
     * Este método copia os valores do objeto do modelo de entrada para o objeto
     * do modelo de domínio.
     *
     * @param input (Objeto do modelo de entrada)
     * @param objectModel (Objeto do modelo de domínio)
     */
    public void copyToObjectModel(AutorInput input, Autor objectModel) {
        mapper.map(input, objectModel);
    }
}
