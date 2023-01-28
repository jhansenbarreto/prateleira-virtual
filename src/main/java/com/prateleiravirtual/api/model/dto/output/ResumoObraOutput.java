package com.prateleiravirtual.api.model.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe criada para evitar expor o modelo do domínio utilizando o padrão de
 * projeto DTO (Data Transfer Object). Esta classe é utilizada para a API servir
 * as informações equivalente à classe Obra, localizada no pacote domain.model,
 * porém, de maneira resumida.
 *
 * @author Jhansen Barreto
 */
@Getter
@Setter
@Schema(description = "Representação de uma obra com informações resumidas. Dados servidos na solicitação de um recurso.")
public class ResumoObraOutput {

    @Schema(description = "ID (identificador) da obra", example = "1")
    private Long id;

    @Schema(description = "Título da obra", example = "Anjos e Demônios")
    private String titulo;

    @Schema(description = "Subtítulo da obra", example = "null")
    private String subtitulo;

    @Schema(description = "Ano de publicação da obra", example = "2000")
    private Integer ano;

    @Schema(description = "Descrição do tipo da obra", example = "LIVRO")
    private String tipo;

    @Schema(description = "Descrição do gênero da obra", example = "ROMANCE")
    private String genero;

    @Schema(description = "Nome da editora da obra", example = "ARQUEIRO")
    private String editora;

    @Schema(description = "URL de acesso à imagem de capa da obra", example = "https://app.box.com/shared/static/exemplo.png")
    private String imagemCapa;
}
