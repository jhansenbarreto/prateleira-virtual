package com.prateleiravirtual.api.controller;

import com.prateleiravirtual.api.controller.openapi.ObraAutorControllerOpenAPI;
import com.prateleiravirtual.domain.service.ObraService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/obras/{obraId}/autores")
public class ObraAutorController implements ObraAutorControllerOpenAPI {

    @Autowired
    private ObraService service;

    @Override
    @PutMapping("/{autorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vincularAutor(@PathVariable Long obraId, @PathVariable Long autorId) {
        service.vincularAutor(service.buscar(obraId), autorId);
    }

    @Override
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vincularAutores(@PathVariable Long obraId, @RequestBody List<Long> ids) {
        service.vincularAutores(service.buscar(obraId), ids);
    }

    @Override
    @DeleteMapping("/{autorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desvincularAutor(@PathVariable Long obraId, @PathVariable Long autorId) {
        service.desvincularAutor(service.buscar(obraId), autorId);
    }

    @Override
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desvincularAutores(@PathVariable Long obraId, @RequestBody List<Long> ids) {
        service.desvincularAutores(service.buscar(obraId), ids);
    }
}
