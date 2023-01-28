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
@Tag(name = "Imagens de Obras", description = "Gerencia imagens de obras")
public interface ImagemObraControllerOpenAPI {

    @Operation(summary = "Download da imagem de capa de uma obra por ID")
    public ResponseEntity<?> downloadImagem(
            @Parameter(
                    description = "ID de uma obra",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id);

    @Operation(summary = "Busca o recurso da representação da imagem de capa de uma obra por ID")
    public ImagemOutput buscarImagem(
            @Parameter(
                    description = "ID de uma obra",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id);

    @Operation(summary = "Atualiza a imagem de capa de uma obra por ID")
    public ImagemOutput atualizarImagem(
            @Parameter(
                    description = "ID de uma obra",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id,
            @RequestBody(
                    description = "Representação do recurso de uma imagem"
            ) ImagemInput imagem) throws IOException;

    @Operation(summary = "Exclui a imagem de capa de uma obra por ID")
    public void removerImagem(
            @Parameter(
                    description = "ID de uma obra",
                    example = "1",
                    schema = @Schema(type = "long")
            ) Long id);
}
