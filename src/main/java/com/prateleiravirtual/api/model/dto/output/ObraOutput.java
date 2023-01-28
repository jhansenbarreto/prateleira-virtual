package com.prateleiravirtual.api.model.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe criada para evitar expor o modelo do domínio utilizando o padrão de
 * projeto DTO (Data Transfer Object). Esta classe é utilizada para a API servir
 * as informações equivalente à classe Obra, localizada no pacote domain.model.
 *
 * @author Jhansen Barreto
 */
@Getter
@Setter
@Schema(description = "Representação de uma obra. Dados servidos na solicitação de um recurso.")
public class ObraOutput {

    @Schema(description = "ID (identificador) da obra", example = "1")
    private Long id;

    @Schema(description = "Título da obra", example = "IT")
    private String titulo;

    @Schema(description = "Subtítulo da obra", example = "A Coisa")
    private String subtitulo;

    @Schema(description = "Ano de publicação da obra", example = "1986")
    private Integer ano;

    @Schema(description = "Breve descrição da obra", example = "Uma snopse da obra")
    private String descricao;

    @Schema(description = "Quantidade de páginas da obra", example = "1104")
    private Integer paginas;

    //Descrição na própria classe
    private TipoObraOutput tipo;

    //Descrição na própria classe
    private GeneroObraOutput genero;

    //Descrição na própria classe
    private EditoraOutput editora;

    @Schema(description = "URL de acesso à imagem de capa da obra", example = "https://app.box.com/shared/static/exemplo.png")
    private String imagemCapa;

    @Schema(description = "Coleção com autores associados à obra")
    private List<AutorOutput> autores;
}
