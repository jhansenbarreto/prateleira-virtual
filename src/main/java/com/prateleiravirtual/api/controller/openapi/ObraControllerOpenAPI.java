package com.prateleiravirtual.api.controller.openapi;

import com.prateleiravirtual.api.model.dto.input.ObraInput;
import com.prateleiravirtual.api.model.dto.output.ObraOutput;
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
@Tag(name = "Obras", description = "Gerencia obras")
public interface ObraControllerOpenAPI {

    @Operation(summary = "Lista todas as obras de um autor por ID")
    public List<ResumoObraOutput> listarObrasPorAutor(
            @Parameter(
                    description = "ID de um autor", 
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long autorId);

    @Operation(summary = "Lista todas as obras de um tipo por ID")
    public List<ResumoObraOutput> listarObrasPorTipo(
            @Parameter(
                    description = "ID de um tipo", 
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long tipoId);

    @Operation(summary = "Lista todas as obras de um gênero por ID")
    public List<ResumoObraOutput> listarObrasPorGenero(
            @Parameter(
                    description = "ID de um gênero", 
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long generoId);

    @Operation(summary = "Lista todas as obras de uma editora por ID")
    public List<ResumoObraOutput> listarObrasPorEditora(
            @Parameter(
                    description = "ID de uma editora", 
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long editoraId);

    @Operation(summary = "Lista todas as obras cadastradas")
    public List<ResumoObraOutput> listar();

    @Operation(summary = "Busca uma obra por ID")
    public ObraOutput buscar(
            @Parameter(
                    description = "ID de uma obra", 
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id);

    @Operation(summary = "Lista todas as obras pelo título ou subtítulo ou parte dos mesmos")
    public List<ResumoObraOutput> pesquisar(
            @Parameter(
                    description = "Título ou Subtítulo completo de uma obra ou parte dos mesmos",
                    example = "a"
            ) String tituloSubtitulo);

    @Operation(summary = "Cadastra uma obra")
    public ObraOutput adicionar(
            @RequestBody(
                    description = "Representação de uma nova obra"
            ) ObraInput input);

    @Operation(summary = "Atualiza uma obra por ID")
    public ObraOutput atualizar(
            @Parameter(
                    description = "ID de uma obra", 
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id,
            @RequestBody(
                    description = "Representação de uma obra com os novos dados"
            ) ObraInput input);

    @Operation(summary = "Exclui uma obra por ID")
    public void remover(
            @Parameter(
                    description = "ID de uma obra", 
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id);
}
