package com.prateleiravirtual.api.controller.openapi;

import com.prateleiravirtual.api.model.dto.input.ImagemInput;
import com.prateleiravirtual.api.model.dto.output.ImagemOutput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;
import org.springframework.http.ResponseEntity;

/**
 * Interface criada para separar da implementação do código, a documentação
 * utilizando Swagger UI com as especificações do OpenAPI 3.0
 *
 * @author Jhansen Barreto
 */
@Tag(name = "Imagens de Autores", description = "Gerencia imagens de autores")
public interface ImagemAutorControllerOpenAPI {

    @Operation(summary = "Download da imagem de um autor por ID")
    public ResponseEntity<?> downloadImagem(
            @Parameter(
                    description = "ID de um autor",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id);

    @Operation(summary = "Busca o recurso da representação da imagem de um autor por ID")
    public ImagemOutput buscarImagem(
            @Parameter(
                    description = "ID de um autor",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id);

    @Operation(summary = "Atualiza a imagem de um autor por ID")
    public ImagemOutput atualizarImagem(
            @Parameter(
                    description = "ID de um autor",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id,
            @RequestBody(
                    description = "Representação do recurso de uma imagem"
            ) ImagemInput imagem) throws IOException;

    @Operation(summary = "Exclui a imagem de um autor por ID")
    public void removerImagem(
            @Parameter(
                    description = "ID de um autor",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id);
}
