package com.prateleiravirtual.api.controller;

import com.prateleiravirtual.api.assembler.ObraConvert;
import com.prateleiravirtual.api.controller.openapi.ObraControllerOpenAPI;

import com.prateleiravirtual.api.model.dto.input.ObraInput;
import com.prateleiravirtual.api.model.dto.output.ObraOutput;
import com.prateleiravirtual.api.model.dto.output.ResumoObraOutput;

import com.prateleiravirtual.domain.service.AutorService;
import com.prateleiravirtual.domain.service.EditoraService;
import com.prateleiravirtual.domain.service.GeneroObraService;
import com.prateleiravirtual.domain.service.ObraService;
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
@RequestMapping("/obras")
public class ObraController implements ObraControllerOpenAPI {

    @Autowired
    private ObraService obraService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private GeneroObraService generoService;

    @Autowired
    private TipoObraService tipoService;

    @Autowired
    private EditoraService editoraService;

    @Autowired
    private ObraConvert obraConvert;

    @Override
    @GetMapping("/autor/{autorId}")
    public List<ResumoObraOutput> listarObrasPorAutor(@PathVariable Long autorId) {
        return obraConvert.toListResumoOutput(autorService.buscar(autorId).getObras());
    }

    @Override
    @GetMapping("/tipo/{tipoId}")
    public List<ResumoObraOutput> listarObrasPorTipo(@PathVariable Long tipoId) {
        return obraConvert.toListResumoOutput(tipoService.buscar(tipoId).getObras());
    }

    @Override
    @GetMapping("/genero/{generoId}")
    public List<ResumoObraOutput> listarObrasPorGenero(@PathVariable Long generoId) {
        return obraConvert.toListResumoOutput(generoService.buscar(generoId).getObras());
    }

    @Override
    @GetMapping("/editora/{editoraId}")
    public List<ResumoObraOutput> listarObrasPorEditora(@PathVariable Long editoraId) {
        return obraConvert.toListResumoOutput(editoraService.buscar(editoraId).getObras());
    }

    @Override
    @GetMapping
    public List<ResumoObraOutput> listar() {
        return obraConvert.toListResumoOutput(obraService.listar());
    }

    @Override
    @GetMapping("/{id}")
    public ObraOutput buscar(@PathVariable Long id) {
        return obraConvert.toOutput(obraService.buscar(id));
    }

    @Override
    @GetMapping("/pesquisar/{tituloSubtitulo}")
    public List<ResumoObraOutput> pesquisar(@PathVariable String tituloSubtitulo) {
        return obraConvert.toListResumoOutput(obraService.buscarPorTituloSubtitulo(tituloSubtitulo));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ObraOutput adicionar(@RequestBody @Valid ObraInput input) {
        return obraConvert.toOutput(obraService.salvar(obraConvert.toObjectModel(input), input.getAutores()));
    }

    @Override
    @PutMapping("/{id}")
    public ObraOutput atualizar(@PathVariable Long id, @RequestBody @Valid ObraInput input) {
        var obra = obraService.buscar(id);
        obraConvert.copyToObjectModel(input, obra);
        return obraConvert.toOutput(obraService.salvar(obra, input.getAutores()));
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        obraService.excluir(id);
    }
}
