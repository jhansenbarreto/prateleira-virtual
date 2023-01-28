package com.prateleiravirtual.api.controller;

import com.prateleiravirtual.api.assembler.AutorConvert;
import com.prateleiravirtual.api.assembler.ObraConvert;

import com.prateleiravirtual.api.controller.openapi.AutorControllerOpenAPI;

import com.prateleiravirtual.api.model.dto.input.AutorInput;
import com.prateleiravirtual.api.model.dto.output.AutorOutput;
import com.prateleiravirtual.api.model.dto.output.ResumoObraOutput;
import com.prateleiravirtual.domain.service.AutorService;

import jakarta.validation.Valid;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autores")
public class AutorController implements AutorControllerOpenAPI {

    @Autowired
    private AutorService service;

    @Autowired
    private AutorConvert convert;

    @Autowired
    private ObraConvert obraConvert;

    @Override
    @GetMapping("/{id}/obras")
    public List<ResumoObraOutput> listarObras(@PathVariable Long id) {
        return obraConvert.toListResumoOutput(service.buscar(id).getObras());
    }

    @Override
    @GetMapping
    public List<AutorOutput> listar() {
        return convert.toListOutput(service.listar());
    }

    @Override
    @GetMapping("/{id}")
    public AutorOutput buscar(@PathVariable Long id) {
        return convert.toOutput(service.buscar(id));
    }

    @Override
    @GetMapping("/pesquisar/{nome}")
    public List<AutorOutput> pesquisar(@PathVariable String nome) {
        return convert.toListOutput(service.buscarPorNome(nome));
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AutorOutput adicionar(@RequestBody @Valid AutorInput input) {
        return convert.toOutput(service.salvar(convert.toObjectModel(input)));
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AutorOutput atualizar(@PathVariable Long id, @RequestBody @Valid AutorInput input) {
        var autor = service.buscar(id);
        convert.copyToObjectModel(input, autor);
        return convert.toOutput(service.salvar(autor));
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.excluir(id);
    }
}
