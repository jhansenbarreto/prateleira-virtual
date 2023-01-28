package com.prateleiravirtual.api.controller.openapi;

import com.prateleiravirtual.api.model.dto.input.EditoraInput;
import com.prateleiravirtual.api.model.dto.output.EditoraOutput;
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
@Tag(name = "Editoras", description = "Gerencia editoras")
public interface EditoraControllerOpenAPI {

    @Operation(summary = "Lista todas as obras de uma editora por ID")
    public List<ResumoObraOutput> listarObras(
            @Parameter(
                    description = "ID de uma editora", 
                    example = "1", 
                    schema = @Schema(type = "long")
            ) Long id);

    @Operation(summary = "Lista todas as editoras cadastradas")
    public List<EditoraOutput> listar();

    @Operation(summary = "Busca uma editora por ID")
    public EditoraOutput buscar(
            @Parameter(
                    description = "ID de uma editora", 
                    example = "1", 
                    schema = @Schema(type = "long")
            ) Long id);

    @Operation(summary = "Lista todas as editoras pelo nome ou parte do mesmo")
    public List<EditoraOutput> pesquisar(
            @Parameter(
                    description = "Nome completo de uma editora ou parte do mesmo",
                    example = "a"
            ) String nome);

    @Operation(summary = "Cadastra uma editora")
    public EditoraOutput adicionar(
            @RequestBody(
                    description = "Representação de uma nova editora"
            ) EditoraInput input);

    @Operation(summary = "Atualiza uma editora por ID")
    public EditoraOutput atualizar(
            @Parameter(
                    description = "ID de uma editora",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id,
            @RequestBody(
                    description = "Representação de uma editora com os novos dados"
            ) EditoraInput input);

    @Operation(summary = "Exclui uma editora por ID")
    public void remover(
            @Parameter(
                    description = "ID de uma editora",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id);
}
