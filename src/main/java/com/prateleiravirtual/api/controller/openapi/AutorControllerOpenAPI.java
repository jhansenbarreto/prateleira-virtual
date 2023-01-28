package com.prateleiravirtual.api.controller.openapi;

import com.prateleiravirtual.api.model.dto.input.AutorInput;
import com.prateleiravirtual.api.model.dto.output.AutorOutput;
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
@Tag(name = "Autores", description = "Gerencia autores")
public interface AutorControllerOpenAPI {

    @Operation(summary = "Lista todas as obras de um autor por ID")
    public List<ResumoObraOutput> listarObras(
            @Parameter(
                    description = "ID de um autor", 
                    example = "1", 
                    schema = @Schema(type = "long")
            ) Long id);

    @Operation(summary = "Lista todos os autores cadastrados")
    public List<AutorOutput> listar();

    @Operation(summary = "Busca um autor por ID")
    public AutorOutput buscar(
            @Parameter(
                    description = "ID de um autor", 
                    example = "1", 
                    schema = @Schema(type = "long")
            ) Long id);

    @Operation(summary = "Lista todos os autores pelo nome ou parte do mesmo")
    public List<AutorOutput> pesquisar(
            @Parameter(
                    description = "Nome completo de um autor ou parte do mesmo", 
                    example = "a"
            ) String nome);

    @Operation(summary = "Cadastra um autor")
    public AutorOutput adicionar(
            @RequestBody(
                    description = "Representação de um novo autor"
            ) AutorInput input);

    @Operation(summary = "Atualiza um autor por ID")
    public AutorOutput atualizar(
            @Parameter(description = "ID de um autor", 
                    example = "1", 
                    schema = @Schema(type = "long")
            ) Long id,
            @RequestBody(
                    description = "Representação de um autor com os novos dados"
            ) AutorInput input);

    @Operation(summary = "Exclui um autor por ID")
    public void remover(
            @Parameter(
                    description = "ID de um autor", 
                    example = "1", 
                    schema = @Schema(type = "long")
            ) Long id);
}
