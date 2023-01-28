package com.prateleiravirtual.api.assembler;

import com.prateleiravirtual.api.model.dto.input.ObraInput;
import com.prateleiravirtual.api.model.dto.output.ObraOutput;
import com.prateleiravirtual.api.model.dto.output.ResumoObraOutput;
import com.prateleiravirtual.domain.model.Editora;
import com.prateleiravirtual.domain.model.GeneroObra;
import com.prateleiravirtual.domain.model.Obra;
import com.prateleiravirtual.domain.model.TipoObra;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe destinada a conversão de objetos. Específica para conversão entre os
 * tipos: Obra (modelo de domínio), ObraInput (modelo de entrada), ObraOutput e
 * ResumoObraOutput (modelos de saída). Os modelos de entrada e saída foram
 * implementados seguindo o padrão de projetos DTO (Data Transfer Object).
 *
 * @author Jhansen Barreto
 */
@Component
public class ObraConvert {

    @Autowired
    private ModelMapper mapper;

    /**
     * Este método recebe um objeto do modelo do domínio e converte-o para o
     * modelo de saída.
     *
     * @param objectModel (Objeto do modelo de domínio)
     * @return -> Objeto do modelo de saída
     */
    public ObraOutput toOutput(Obra objectModel) {
        return mapper.map(objectModel, ObraOutput.class);
    }

    /**
     * Este método recebe um objeto do modelo do domínio e converte-o para o
     * modelo de saída.
     *
     * @param objectModel (Objeto do modelo de domínio)
     * @return -> Objeto do modelo de saída, com informações reduzidas
     */
    private ResumoObraOutput toResumoOutput(Obra objectModel) {
        return mapper.map(objectModel, ResumoObraOutput.class);
    }

    /**
     * Este método recebe um objeto do modelo de entrada e converte-o para o
     * modelo de domínio.
     *
     * @param input (Objeto do modelo de entrada)
     * @return -> Objeto do modelo de domínio
     */
    public Obra toObjectModel(ObraInput input) {
        return mapper.map(input, Obra.class);
    }

    /**
     * Este método recebe uma coleção com objetos do tipo do modelo de domínio e
     * os converte para o modelo de saída, servindo uma lista.
     *
     * @param collection (Coleção do modelo de domínio)
     * @return -> Lista do modelo de saída
     */
    public List<ObraOutput> toListOutput(Collection<Obra> collection) {
        return collection.stream().map(object -> toOutput(object)).collect(Collectors.toList());
    }

    /**
     * Este método recebe uma coleção com objetos do tipo do modelo de domínio e
     * os converte para o modelo de saída (resumido), servindo uma lista.
     *
     * @param collection (Coleção do modelo de domínio)
     * @return -> Lista do modelo de saída, com informações reduzidas
     */
    public List<ResumoObraOutput> toListResumoOutput(Collection<Obra> collection) {
        return collection.stream().map(object -> toResumoOutput(object)).collect(Collectors.toList());
    }

    /**
     * Este método copia os valores do objeto do modelo de entrada para o objeto
     * do modelo de domínio.
     *
     * @param input (Objeto do modelo de entrada)
     * @param objectModel (Objeto do modelo de domínio)
     */
    public void copyToObjectModel(ObraInput input, Obra objectModel) {
        objectModel.setEditora(new Editora());
        objectModel.setGenero(new GeneroObra());
        objectModel.setTipo(new TipoObra());
        mapper.map(input, objectModel);
    }
}
