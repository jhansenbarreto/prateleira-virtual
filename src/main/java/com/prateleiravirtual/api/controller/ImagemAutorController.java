package com.prateleiravirtual.api.controller;

import static com.prateleiravirtual.PrateleiraVirtualApplication.GlobalConstants.*;

import com.prateleiravirtual.api.assembler.ImagemConvert;

import com.prateleiravirtual.api.controller.openapi.ImagemAutorControllerOpenAPI;
import com.prateleiravirtual.api.controller.utils.Utilidades;
import com.prateleiravirtual.api.model.dto.input.ImagemInput;

import com.prateleiravirtual.api.model.dto.output.ImagemOutput;

import com.prateleiravirtual.domain.exception.ImagemNaoEncontradaException;
import com.prateleiravirtual.domain.model.Autor;
import com.prateleiravirtual.domain.model.ImagemDetalhes;
import com.prateleiravirtual.domain.service.AutorService;

import jakarta.validation.Valid;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/autores/{id}/imagem")
public class ImagemAutorController implements ImagemAutorControllerOpenAPI {

    @Autowired
    private AutorService service;

    @Autowired
    private ImagemConvert imagemConvert;

    @Autowired
    private Utilidades utils;

    @Override
    @GetMapping("/download")
    public ResponseEntity<?> downloadImagem(@PathVariable Long id) {
        var autor = service.buscar(id);
        var imagem = autor.getImagem();

        if (imagem == null) {
            throw new ImagemNaoEncontradaException(id, ImagemNaoEncontradaException.TipoImagem.AUTOR);
        }

        var stream = service.buscarImagem(imagem.getDetalhes().getNomeArquivo());
        var type = MediaType.parseMediaType(imagem.getDetalhes().getContentType());

        return ResponseEntity.ok().contentType(type).body(new InputStreamResource(stream));
    }

    @Override
    @GetMapping
    public ImagemOutput buscarImagem(@PathVariable Long id) {
        var autor = service.buscar(id);
        var imagem = autor.getImagem();

        if (imagem == null) {
            throw new ImagemNaoEncontradaException(id, ImagemNaoEncontradaException.TipoImagem.AUTOR);
        }
        return imagemConvert.toOutput(imagem);
    }

    @Override
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ImagemOutput atualizarImagem(@PathVariable Long id, @Valid ImagemInput imagem) throws IOException {
        var autor = montarImagem(service.buscar(id), imagem.getArquivo());
        return imagemConvert.toOutput(autor.getImagem());
    }

    @Override
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerImagem(@PathVariable Long id) {
        var autor = service.buscar(id);

        if (autor.getImagem() == null) {
            throw new ImagemNaoEncontradaException(id, ImagemNaoEncontradaException.TipoImagem.AUTOR);
        }
        service.excluirImagem(autor);
    }

    private Autor montarImagem(Autor autor, MultipartFile arquivo) throws IOException {
        var detalhes = new ImagemDetalhes(
                novoNomeArquivo(arquivo.getOriginalFilename()),
                arquivo.getContentType(),
                arquivo.getSize(),
                null
        );
        service.salvarImagem(autor, detalhes, arquivo.getInputStream());
        return autor;
    }

    private String novoNomeArquivo(String originalFilename) {
        return utils.gerarNomeArquivo(AUTOR, originalFilename);
    }
}
