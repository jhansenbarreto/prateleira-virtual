package com.prateleiravirtual.api.controller;

import com.prateleiravirtual.api.assembler.ObraConvert;
import com.prateleiravirtual.api.assembler.TipoObraConvert;
import com.prateleiravirtual.api.controller.openapi.TipoObraControllerOpenAPI;
import com.prateleiravirtual.api.model.dto.input.TipoObraInput;
import com.prateleiravirtual.api.model.dto.output.TipoObraOutput;
import com.prateleiravirtual.api.model.dto.output.ResumoObraOutput;
import com.prateleiravirtual.domain.service.TipoObraService;

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
@RequestMapping("/tipos-obra")
public class TipoObraController implements TipoObraControllerOpenAPI {

    @Autowired
    private TipoObraService service;

    @Autowired
    private TipoObraConvert convert;

    @Autowired
    private ObraConvert obraConvert;

    @Override
    @GetMapping("/{id}/obras")
    public List<ResumoObraOutput> listarObras(@PathVariable Long id) {
        return obraConvert.toListResumoOutput(service.buscar(id).getObras());
    }

    @Override
    @GetMapping
    public List<TipoObraOutput> listar() {
        return convert.toListOutput(service.listar());
    }

    @Override
    @GetMapping("/{id}")
    public TipoObraOutput buscar(@PathVariable Long id) {
        return convert.toOutput(service.buscar(id));
    }

    @Override
    @GetMapping("/pesquisar/{descricao}")
    public List<TipoObraOutput> pesquisar(@PathVariable String descricao) {
        return convert.toListOutput(service.buscarPorDescricao(descricao));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoObraOutput adicionar(@RequestBody @Valid TipoObraInput input) {
        return convert.toOutput(service.salvar(convert.toObjectModel(input)));
    }

    @Override
    @PutMapping("/{id}")
    public TipoObraOutput atualizar(@PathVariable Long id, @RequestBody @Valid TipoObraInput input) {
        var tipo = service.buscar(id);
        convert.copyToObjectModel(input, tipo);
        return convert.toOutput(service.salvar(tipo));
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.excluir(id);
    }
}
