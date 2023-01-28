package com.prateleiravirtual.api.controller;

import com.prateleiravirtual.api.controller.openapi.UsuarioObraControllerOpenAPI;

import com.prateleiravirtual.domain.service.UsuarioObraService;
import com.prateleiravirtual.domain.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios/{usuarioId}/obras/{obraId}")
public class UsuarioObraController implements UsuarioObraControllerOpenAPI {

    @Autowired
    private UsuarioObraService usuarioObraService;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    @PutMapping("/ja-li")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vincularObraLida(@PathVariable Long usuarioId, @PathVariable Long obraId) {
        usuarioObraService.vincularOuDesvincularObra(
                usuarioService.buscar(usuarioId),
                obraId,
                UsuarioObraService.TipoLista.OBRAS_LIDAS,
                true
        );
    }

    @Override
    @DeleteMapping("/ja-li")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desvincularObraLida(@PathVariable Long usuarioId, @PathVariable Long obraId) {
        usuarioObraService.vincularOuDesvincularObra(
                usuarioService.buscar(usuarioId),
                obraId,
                UsuarioObraService.TipoLista.OBRAS_LIDAS,
                false
        );
    }

    @Override
    @PutMapping("/quero-ler")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vincularObraParaLer(@PathVariable Long usuarioId, @PathVariable Long obraId) {
        usuarioObraService.vincularOuDesvincularObra(
                usuarioService.buscar(usuarioId),
                obraId,
                UsuarioObraService.TipoLista.OBRAS_PARA_LER,
                true
        );
    }

    @Override
    @DeleteMapping("/quero-ler")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desvincularObraParaLer(@PathVariable Long usuarioId, @PathVariable Long obraId) {
        usuarioObraService.vincularOuDesvincularObra(
                usuarioService.buscar(usuarioId),
                obraId,
                UsuarioObraService.TipoLista.OBRAS_PARA_LER,
                false
        );
    }

    @Override
    @PutMapping("/estou-lendo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vincularObraEmLeitura(@PathVariable Long usuarioId, @PathVariable Long obraId) {
        usuarioObraService.vincularOuDesvincularObra(
                usuarioService.buscar(usuarioId),
                obraId,
                UsuarioObraService.TipoLista.OBRAS_EM_LEITURA,
                true
        );
    }

    @Override
    @DeleteMapping("/estou-lendo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desvincularObraEmLeitura(@PathVariable Long usuarioId, @PathVariable Long obraId) {
        usuarioObraService.vincularOuDesvincularObra(
                usuarioService.buscar(usuarioId),
                obraId,
                UsuarioObraService.TipoLista.OBRAS_EM_LEITURA,
                false
        );
    }
}
