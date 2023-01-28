package com.prateleiravirtual.api.controller;

import com.prateleiravirtual.api.assembler.ObraConvert;
import com.prateleiravirtual.api.assembler.UsuarioConvert;
import com.prateleiravirtual.api.controller.openapi.UsuarioControllerOpenAPI;
import com.prateleiravirtual.api.controller.utils.Utilidades;

import com.prateleiravirtual.api.model.dto.input.AtivacaoInput;
import com.prateleiravirtual.api.model.dto.input.EmailInput;
import com.prateleiravirtual.api.model.dto.input.RedefinirSenhaInput;
import com.prateleiravirtual.api.model.dto.input.SenhaInput;
import com.prateleiravirtual.api.model.dto.input.UsuarioInput;
import com.prateleiravirtual.api.model.dto.input.UsuarioUpdateInput;
import com.prateleiravirtual.api.model.dto.output.ResumoObraOutput;
import com.prateleiravirtual.api.model.dto.output.UsuarioOutput;

import com.prateleiravirtual.domain.exception.ErroRegraNegocioException;
import com.prateleiravirtual.domain.service.UsuarioService;

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
@RequestMapping("/usuarios")
public class UsuarioController implements UsuarioControllerOpenAPI {

    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioConvert convert;

    @Autowired
    private ObraConvert obraConvert;

    @Autowired
    private Utilidades utils;

    @Override
    @GetMapping
    public List<UsuarioOutput> listar() {
        return convert.toListOutput(service.listar());
    }

    @Override
    @GetMapping("/{id}")
    public UsuarioOutput buscar(@PathVariable Long id) {
        return convert.toOutput(service.buscar(id));
    }

    @Override
    @GetMapping("/pesquisar/{username}")
    public List<UsuarioOutput> pesquisar(@PathVariable String username) {
        return convert.toListOutput(service.buscarPorUsername(username));
    }

    @Override
    @GetMapping("/{id}/ja-li")
    public List<ResumoObraOutput> obrasLidas(@PathVariable Long id) {
        return obraConvert.toListResumoOutput(service.buscar(id).getObrasLidas());
    }

    @Override
    @GetMapping("/{id}/estou-lendo")
    public List<ResumoObraOutput> obrasEmLeitura(@PathVariable Long id) {
        return obraConvert.toListResumoOutput(service.buscar(id).getObrasEmLeitura());
    }

    @Override
    @GetMapping("/{id}/quero-ler")
    public List<ResumoObraOutput> obrasParaLer(@PathVariable Long id) {
        return obraConvert.toListResumoOutput(service.buscar(id).getObrasParaLer());
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioOutput adicionar(@RequestBody @Valid UsuarioInput input) {

        conferirSenha(input.getSenha(), input.getConfirmacaoSenha());
        return convert.toOutput(
                service.salvar(
                        convert.toObjectModel(input),
                        utils.gerarCharsAleatorios(8)
                )
        );
    }

    @Override
    @PutMapping("{id}/ativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long id, @RequestBody @Valid AtivacaoInput codigo) {
        service.ativarUsuario(
                service.buscar(id).getId(),
                codigo.getCodigo()
        );
    }

    @Override
    @DeleteMapping("/{id}/desativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativar(@PathVariable Long id) {
        service.desativarUsuario(service.buscar(id));
    }

    @Override
    @PostMapping("/reenviar-email-ativacao")
    public void reenviarEmailAtivacao(@RequestBody @Valid EmailInput input) {
        service.salvarAtivacaoUsuario(
                service.buscarPorEmail(input.getEmail()),
                utils.gerarCharsAleatorios(8)
        );
    }

    @Override
    @PutMapping("/{id}")
    public UsuarioOutput atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioUpdateInput input) {
        var usuario = service.buscar(id);
        convert.copyToObjectModel(input, usuario);
        return convert.toOutput(service.salvar(usuario, null));
    }

    @Override
    @PutMapping("/{id}/alterar-senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaInput input) {
        var usuario = service.buscar(id);
        conferirSenha(input.getNovaSenha(), input.getConfirmacaoSenha());

        service.alterarSenha(usuario, input.getSenhaAtual(), input.getNovaSenha());
    }

    @Override
    @PostMapping("/esqueceu-senha")
    public void esqueceuSenha(@RequestBody @Valid EmailInput input) {
        service.salvarRedefinicaoSenha(
                service.buscarPorEmail(input.getEmail()),
                utils.gerarCharsAleatorios(8)
        );
    }

    @Override
    @PutMapping("/{id}/redefinir-senha-esquecida")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void redefinirSenhaEsquecida(@PathVariable Long id, @RequestBody @Valid RedefinirSenhaInput input) {
        conferirSenha(input.getNovaSenha(), input.getConfirmacaoSenha());
        service.redefinirSenhaEsquecida(
                service.buscar(id).getId(),
                input.getCodigo(),
                input.getNovaSenha()
        );
    }

    @Override
    @PostMapping("/reenviar-email-redefinicao")
    public void reenviarEmailRedefinicao(@RequestBody @Valid EmailInput input) {
        service.salvarRedefinicaoSenha(
                service.buscarPorEmail(input.getEmail()),
                utils.gerarCharsAleatorios(8)
        );
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.excluir(id);
    }

    private void conferirSenha(String senha, String confirmacao) {
        if (!senha.equals(confirmacao)) {
            throw new ErroRegraNegocioException("A senha e a confirmação não coincidem.");
        }
    }
}
