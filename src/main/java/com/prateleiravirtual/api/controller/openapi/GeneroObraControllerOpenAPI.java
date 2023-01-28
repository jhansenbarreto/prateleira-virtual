package com.prateleiravirtual.api.controller.openapi;

import com.prateleiravirtual.api.model.dto.input.GeneroObraInput;
import com.prateleiravirtual.api.model.dto.output.GeneroObraOutput;
import com.prateleiravirtual.api.model.dto.output.ResumoObraOutput;

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
@Tag(name = "Gêneros de Obras", description = "Gerencia gêneros de obras")
public interface GeneroObraControllerOpenAPI {

    @Operation(summary = "Lista todas as obras de um gênero por ID")
    public List<ResumoObraOutput> listarObras(
            @Parameter(
                    description = "ID de um gênero",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id);

    @Operation(summary = "Lista todos os gêneros cadastrados")
    public List<GeneroObraOutput> listar();

    @Operation(summary = "Busca um gênero por ID")
    public GeneroObraOutput buscar(
            @Parameter(
                    description = "ID de um gênero",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id);

    @Operation(summary = "Lista todos os gêneros pela descrição ou parte da mesma")
    public List<GeneroObraOutput> pesquisar(
            @Parameter(
                    description = "Descrição completa de um gênero ou parte da mesma",
                    example = "a"
            ) String descricao);

    @Operation(summary = "Cadastra um gênero")
    public GeneroObraOutput adicionar(
            @RequestBody(
                    description = "Representação de um novo gênero"
            ) GeneroObraInput input);

    @Operation(summary = "Atualiza um gênero por ID")
    public GeneroObraOutput atualizar(
            @Parameter(
                    description = "ID de um gênero",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id,
            @RequestBody(
                    description = "Representação de um gênero com os novos dados"
            ) GeneroObraInput input);

    @Operation(summary = "Exclui um gênero por ID")
    void remover(
            @Parameter(
                    description = "ID de um gênero",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id);
}
