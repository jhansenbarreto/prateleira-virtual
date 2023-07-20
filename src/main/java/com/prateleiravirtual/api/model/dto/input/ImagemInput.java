package com.prateleiravirtual.api.model.dto.input;

import com.prateleiravirtual.core.validation.annotation.FileContentType;
import com.prateleiravirtual.core.validation.annotation.FileSize;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

/**
 * Classe de entrada de dados que representa o recebimento de um arquivo de
 * imagem.
 *
 * @author Jhansen Barreto
 */
@Getter
@Setter
@Schema(description = "Representação dos dados de entrada para atualização de uma imagem (Autor, Obra ou Usuario)")
public class ImagemInput {

    @NotNull
    @FileSize(max = "1MB")
    @Schema(description = "Arquivo de imagem")
    @FileContentType(types = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE},
            message = "Só é permitido arquivo JPG ou PNG.")
    private MultipartFile arquivo;
}
