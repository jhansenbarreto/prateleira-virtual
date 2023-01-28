package com.prateleiravirtual.api.controller;

import com.prateleiravirtual.api.assembler.EditoraConvert;
import com.prateleiravirtual.api.assembler.ObraConvert;

import com.prateleiravirtual.api.controller.openapi.EditoraControllerOpenAPI;

import com.prateleiravirtual.api.model.dto.input.EditoraInput;
import com.prateleiravirtual.api.model.dto.output.EditoraOutput;
import com.prateleiravirtual.api.model.dto.output.ResumoObraOutput;
import com.prateleiravirtual.domain.service.EditoraService;

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
@RequestMapping("/editoras")
public class EditoraController implements EditoraControllerOpenAPI {

    @Autowired
    private EditoraService service;

    @Autowired
    private EditoraConvert convert;

    @Autowired
    private ObraConvert obraConvert;

    @Override
    @GetMapping("/{id}/obras")
    public List<ResumoObraOutput> listarObras(@PathVariable Long id) {
        return obraConvert.toListResumoOutput(service.buscar(id).getObras());
    }

    @Override
    @GetMapping
    public List<EditoraOutput> listar() {
        return convert.toListOutput(service.listar());
    }

    @Override
    @GetMapping("/{id}")
    public EditoraOutput buscar(@PathVariable Long id) {
        return convert.toOutput(service.buscar(id));
    }

    @Override
    @GetMapping("/pesquisar/{nome}")
    public List<EditoraOutput> pesquisar(@PathVariable String nome) {
        return convert.toListOutput(service.buscarPorNome(nome));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EditoraOutput adicionar(@RequestBody @Valid EditoraInput input) {
        return convert.toOutput(service.salvar(convert.toObjectModel(input)));
    }

    @Override
    @PutMapping("/{id}")
    public EditoraOutput atualizar(@PathVariable Long id, @RequestBody @Valid EditoraInput input) {
        var editora = service.buscar(id);
        convert.copyToObjectModel(input, editora);
        return convert.toOutput(service.salvar(editora));
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.excluir(id);
    }
}
