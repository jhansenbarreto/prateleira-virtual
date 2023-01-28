package com.prateleiravirtual.api.controller;

import static com.prateleiravirtual.PrateleiraVirtualApplication.GlobalConstants.*;

import com.prateleiravirtual.api.assembler.ImagemConvert;
import com.prateleiravirtual.api.controller.openapi.ImagemObraControllerOpenAPI;
import com.prateleiravirtual.api.controller.utils.Utilidades;
import com.prateleiravirtual.api.model.dto.input.ImagemInput;
import com.prateleiravirtual.api.model.dto.output.ImagemOutput;

import com.prateleiravirtual.domain.exception.ImagemNaoEncontradaException;
import com.prateleiravirtual.domain.model.ImagemDetalhes;
import com.prateleiravirtual.domain.model.Obra;
import com.prateleiravirtual.domain.service.ObraService;

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
@RequestMapping("/obras/{id}/imagem-capa")
public class ImagemObraController implements ImagemObraControllerOpenAPI {

    @Autowired
    private ObraService service;

    @Autowired
    private ImagemConvert imagemConvert;

    @Autowired
    private Utilidades utils;

    @Override
    @GetMapping("/download")
    public ResponseEntity<?> downloadImagem(@PathVariable Long id) {
        var obra = service.buscar(id);
        var imagemCapa = obra.getCapa();

        if (imagemCapa == null) {
            throw new ImagemNaoEncontradaException(id, ImagemNaoEncontradaException.TipoImagem.OBRA);
        }

        var stream = service.buscarImagem(imagemCapa.getDetalhes().getNomeArquivo());
        var type = MediaType.parseMediaType(imagemCapa.getDetalhes().getContentType());

        return ResponseEntity.ok().contentType(type).body(new InputStreamResource(stream));
    }

    @Override
    @GetMapping
    public ImagemOutput buscarImagem(@PathVariable Long id) {
        var imagem = service.buscar(id).getCapa();

        if (imagem == null) {
            throw new ImagemNaoEncontradaException(id, ImagemNaoEncontradaException.TipoImagem.OBRA);
        }
        return imagemConvert.toOutput(imagem);
    }

    @Override
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ImagemOutput atualizarImagem(@PathVariable Long id, @Valid ImagemInput imagem) throws IOException {
        var obra = montarImagem(service.buscar(id), imagem.getArquivo());
        return imagemConvert.toOutput(obra.getCapa());
    }

    @Override
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerImagem(@PathVariable Long id) {
        var obra = service.buscar(id);

        if (obra.getCapa() == null) {
            throw new ImagemNaoEncontradaException(id, ImagemNaoEncontradaException.TipoImagem.OBRA);
        }
        service.excluirImagem(obra);
    }

    private Obra montarImagem(Obra obra, MultipartFile arquivo) throws IOException {
        var detalhes = new ImagemDetalhes(
                novoNomeArquivo(arquivo.getOriginalFilename()),
                arquivo.getContentType(),
                arquivo.getSize(),
                null
        );
        service.salvarImagem(obra, detalhes, arquivo.getInputStream());
        return obra;
    }

    private String novoNomeArquivo(String originalFilename) {
        return utils.gerarNomeArquivo(OBRA, originalFilename);
    }
}
