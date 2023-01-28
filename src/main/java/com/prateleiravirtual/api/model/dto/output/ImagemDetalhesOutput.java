package com.prateleiravirtual.api.model.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe criada para evitar expor o modelo do domínio utilizando o padrão de
 * projeto DTO (Data Transfer Object). Esta classe é utilizada para a API servir
 * as informações equivalente à classe ImagemDetalhes, localizada no pacote
 * domain.model.
 *
 * @author Jhansen Barreto
 */
@Getter
@Setter
@Schema(description = "Classe que representa os detalhes comuns a todas as imagens tratadas nesta API. "
        + "Dados servidos na solicitação de um recurso.")
public class ImagemDetalhesOutput {

    @Schema(description = "Nome do arquivo", example = "OBRA_46ca8047-d3d3-475e-b1be-14c7424628c4.png")
    private String nomeArquivo;

    @Schema(description = "Tipo do arquivo (extensão)", example = "image/png")
    private String contentType;

    @Schema(description = "Tamanho do arquivo em bytes", example = "12345")
    private Long tamanho;

    @Schema(description = "URL de acesso à imagem", example = "https://app.box.com/shared/static/exemplo.png")
    private String url;
}
