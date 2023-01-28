package com.prateleiravirtual.api.assembler;

import com.prateleiravirtual.api.model.dto.input.GeneroObraInput;
import com.prateleiravirtual.api.model.dto.output.GeneroObraOutput;
import com.prateleiravirtual.domain.model.GeneroObra;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe destinada a conversão de objetos. Específica para conversão entre os
 * tipos: GeneroObra (modelo de domínio), GeneroObraInput (modelo de entrada) e
 * GeneroObraOutput (modelo de saída). Os modelos de entrada e saída foram
 * implementados seguindo o padrão de projetos DTO (Data Transfer Object).
 *
 * @author Jhansen Barreto
 */
@Component
public class GeneroObraConvert {

    @Autowired
    private ModelMapper mapper;

    /**
     * Este método recebe um objeto do modelo do domínio e converte-o para o
     * modelo de saída.
     *
     * @param objectModel (Objeto do modelo de domínio)
     * @return -> Objeto do modelo de saída
     */
    public GeneroObraOutput toOutput(GeneroObra objectModel) {
        return mapper.map(objectModel, GeneroObraOutput.class);
    }

    /**
     * Este método recebe um objeto do modelo de entrada e converte-o para o
     * modelo de domínio.
     *
     * @param input (Objeto do modelo de entrada)
     * @return -> Objeto do modelo de domínio
     */
    public GeneroObra toObjectModel(GeneroObraInput input) {
        return mapper.map(input, GeneroObra.class);
    }

    /**
     * Este método recebe uma coleção com objetos do tipo do modelo de domínio e
     * os converte para o modelo de saída, servindo uma lista.
     *
     * @param collection (Coleção do modelo de domínio)
     * @return -> Lista do modelo de saída
     */
    public List<GeneroObraOutput> toListOutput(Collection<GeneroObra> collection) {
        return collection.stream().map(object -> toOutput(object)).collect(Collectors.toList());
    }

    /**
     * Este método copia os valores do objeto do modelo de entrada para o objeto
     * do modelo de domínio.
     *
     * @param input (Objeto do modelo de entrada)
     * @param objectModel (Objeto do modelo de domínio)
     */
    public void copyToObjectModel(GeneroObraInput input, GeneroObra objectModel) {
        mapper.map(input, objectModel);
    }
}
