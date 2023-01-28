package com.prateleiravirtual.api.controller;

import com.prateleiravirtual.api.controller.openapi.GeneroObraControllerOpenAPI;
import com.prateleiravirtual.api.assembler.GeneroObraConvert;
import com.prateleiravirtual.api.assembler.ObraConvert;
import com.prateleiravirtual.api.model.dto.input.GeneroObraInput;
import com.prateleiravirtual.api.model.dto.output.GeneroObraOutput;
import com.prateleiravirtual.api.model.dto.output.ResumoObraOutput;
import com.prateleiravirtual.domain.service.GeneroObraService;

import jakarta.validation.Valid;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/generos-obra")
public class GeneroObraController implements GeneroObraControllerOpenAPI {

    @Autowired
    private GeneroObraService service;

    @Autowired
    private GeneroObraConvert convert;

    @Autowired
    private ObraConvert obraConvert;

    @Override
    @GetMapping("/{id}/obras")
    public List<ResumoObraOutput> listarObras(@PathVariable Long id) {
        return obraConvert.toListResumoOutput(service.buscar(id).getObras());
    }

    @Override
    @GetMapping
    public List<GeneroObraOutput> listar() {
        return convert.toListOutput(service.listar());
    }

    @Override
    @GetMapping("/{id}")
    public GeneroObraOutput buscar(@PathVariable Long id) {
        return convert.toOutput(service.buscar(id));
    }

    @Override
    @GetMapping("/pesquisar/{descricao}")
    public List<GeneroObraOutput> pesquisar(@PathVariable String descricao) {
        return convert.toListOutput(service.buscarPorDescricao(descricao));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GeneroObraOutput adicionar(@RequestBody @Valid GeneroObraInput input) {
        return convert.toOutput(service.salvar(convert.toObjectModel(input)));
    }

    @Override
    @PutMapping("/{id}")
    public GeneroObraOutput atualizar(@PathVariable Long id, @RequestBody @Valid GeneroObraInput input) {
        var genero = service.buscar(id);
        convert.copyToObjectModel(input, genero);
        return convert.toOutput(service.salvar(genero));
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.excluir(id);
    }
}
