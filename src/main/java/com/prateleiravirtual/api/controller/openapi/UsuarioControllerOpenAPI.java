package com.prateleiravirtual.api.controller.openapi;

import com.prateleiravirtual.api.model.dto.input.AtivacaoInput;
import com.prateleiravirtual.api.model.dto.input.EmailInput;
import com.prateleiravirtual.api.model.dto.input.RedefinirSenhaInput;
import com.prateleiravirtual.api.model.dto.input.SenhaInput;
import com.prateleiravirtual.api.model.dto.input.UsuarioInput;
import com.prateleiravirtual.api.model.dto.input.UsuarioUpdateInput;

import com.prateleiravirtual.api.model.dto.output.ResumoObraOutput;
import com.prateleiravirtual.api.model.dto.output.UsuarioOutput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 * Interface criada para separar da implementação do código, a documentação
 * utilizando Swagger UI com as especificações do OpenAPI 3.0
 *
 * @author Jhansen Barreto
 */
@Tag(name = "Usuários", description = "Gerencia usuários")
public interface UsuarioControllerOpenAPI {

    @Operation(summary = "Lista todos os usuários cadastrados")
    public List<UsuarioOutput> listar();

    @Operation(summary = "Busca um usuário por ID")
    public UsuarioOutput buscar(Long id);

    @Operation(summary = "Lista todos os usuários pelo username ou parte do mesmo")
    public List<UsuarioOutput> pesquisar(String username);

    @Operation(summary = "Lista todas as obras já lidas de um usuário por ID")
    public List<ResumoObraOutput> obrasLidas(Long id);

    @Operation(summary = "Lista todas as obras que um usuário está lendo, por ID")
    public List<ResumoObraOutput> obrasEmLeitura(Long id);

    @Operation(summary = "Lista todas as obras que o usuário deseja ler, por ID")
    public List<ResumoObraOutput> obrasParaLer(Long id);

    @Operation(summary = "Cadastra um usuário")
    public UsuarioOutput adicionar(UsuarioInput input);

    @Operation(summary = "Ativa um usuário por ID")
    public void ativar(Long id, AtivacaoInput codigo);

    @Operation(summary = "Desativa um usuário por ID")
    public void desativar(Long id);

    @Operation(summary = "Reenvia o e-mail com código de ativação para um usuário")
    public void reenviarEmailAtivacao(EmailInput input);

    @Operation(summary = "Atualiza um usuário por ID")
    public UsuarioOutput atualizar(Long id, UsuarioUpdateInput input);

    @Operation(summary = "Altera a senha de um usuário por ID")
    public void alterarSenha(Long id, SenhaInput input);

    @Operation(summary = "Solicita um código para redefinir senha esquecida por e-mail")
    public void esqueceuSenha(EmailInput input);

    @Operation(summary = "Redefine a senha esquecida por ID")
    public void redefinirSenhaEsquecida(Long id, RedefinirSenhaInput input);

    @Operation(summary = "Reenvia o e-mail com código de redefinição de senha para um usuário")
    public void reenviarEmailRedefinicao(
            @RequestBody(
                    description = "Representação do e-mail de um usuário"
            ) EmailInput input);

    @Operation(summary = "Exclui um usuário por ID")
    public void remover(
            @Parameter(
                    description = "ID de um usuário",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id);
}
