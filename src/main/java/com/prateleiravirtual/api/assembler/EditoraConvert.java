package com.prateleiravirtual.api.assembler;

import com.prateleiravirtual.api.model.dto.input.EditoraInput;
import com.prateleiravirtual.api.model.dto.output.EditoraOutput;
import com.prateleiravirtual.domain.model.Editora;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe destinada a conversão de objetos. Específica para conversão entre os
 * tipos: Editora (modelo de domínio), EditoraInput (modelo de entrada) e
 * EditoraOutput (modelo de saída). Os modelos de entrada e saída foram
 * implementados seguindo o padrão de projetos DTO (Data Transfer Object).
 *
 * @author Jhansen Barreto
 */
@Component
public class EditoraConvert {

    @Autowired
    private ModelMapper mapper;

    /**
     * Este método recebe um objeto do modelo do domínio e converte-o para o
     * modelo de saída.
     *
     * @param objectModel (Objeto do modelo de domínio)
     * @return -> Objeto do modelo de saída
     */
    public EditoraOutput toOutput(Editora objectModel) {
        return mapper.map(objectModel, EditoraOutput.class);
    }

    /**
     * Este método recebe um objeto do modelo de entrada e converte-o para o
     * modelo de domínio.
     *
     * @param input (Objeto do modelo de entrada)
     * @return -> Objeto do modelo de domínio
     */
    public Editora toObjectModel(EditoraInput input) {
        return mapper.map(input, Editora.class);
    }

    /**
     * Este método recebe uma coleção com objetos do tipo do modelo de domínio e
     * os converte para o modelo de saída, servindo uma lista.
     *
     * @param collection (Coleção do modelo de domínio)
     * @return -> Lista do modelo de saída
     */
    public List<EditoraOutput> toListOutput(Collection<Editora> collection) {
        return collection.stream().map(object -> toOutput(object)).collect(Collectors.toList());
    }

    /**
     * Este método copia os valores do objeto do modelo de entrada para o objeto
     * do modelo de domínio.
     *
     * @param input (Objeto do modelo de entrada)
     * @param objectModel (Objeto do modelo de domínio)
     */
    public void copyToObjectModel(EditoraInput input, Editora objectModel) {
        mapper.map(input, objectModel);
    }
}
