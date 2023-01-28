package com.prateleiravirtual.api.assembler;

import com.prateleiravirtual.api.model.dto.output.ImagemOutput;
import com.prateleiravirtual.domain.model.ImagemAutor;
import com.prateleiravirtual.domain.model.ImagemObra;
import com.prateleiravirtual.domain.model.ImagemUsuario;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe destinada a conversão de objetos. Específica para conversão entre os
 * tipos: ImagemAutor, ImagemObra, ImagemUsuario (modelos de domínio) e
 * ImagemOutput (modelo de saída). Os modelos de entrada e saída foram
 * implementados seguindo o padrão de projetos DTO (Data Transfer Object).
 *
 * @author Jhansen Barreto
 */
@Component
public class ImagemConvert {

    @Autowired
    private ModelMapper mapper;

    /**
     * Este método recebe um objeto relacionado a imagem de autor do modelo do
     * domínio e converte-o para o modelo de saída.
     *
     * @param objectModel (Objeto do modelo de domínio)
     * @return -> Objeto do modelo de saída
     */
    public ImagemOutput toOutput(ImagemAutor objectModel) {
        return mapper.map(objectModel, ImagemOutput.class);
    }

    /**
     * Este método recebe um objeto relacionado a imagem de obra do modelo do
     * domínio e converte-o para o modelo de saída.
     *
     * @param objectModel (Objeto do modelo de domínio)
     * @return -> Objeto do modelo de saída
     */
    public ImagemOutput toOutput(ImagemObra objectModel) {
        return mapper.map(objectModel, ImagemOutput.class);
    }

    /**
     * Este método recebe um objeto relacionado a imagem de usuário do modelo do
     * domínio e converte-o para o modelo de saída.
     *
     * @param objectModel (Objeto do modelo de domínio)
     * @return -> Objeto do modelo de saída
     */
    public ImagemOutput toOutput(ImagemUsuario objectModel) {
        return mapper.map(objectModel, ImagemOutput.class);
    }
}
