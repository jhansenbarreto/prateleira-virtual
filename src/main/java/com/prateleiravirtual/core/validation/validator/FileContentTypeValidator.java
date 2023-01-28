package com.prateleiravirtual.core.validation.validator;

import com.prateleiravirtual.core.validation.annotation.FileContentType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * Classe que implementa a interface para validar restrições. Validador da
 * anotação @FileContentType. Restrição -> A extensão de um arquivo deve estar
 * entre os tipos válidos fornecidos em um array no uso da anotação. Exemplos:
 * .png | .jpg
 *
 * @author Jhansen Barreto
 */
public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List types;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        this.types = Arrays.asList(constraintAnnotation.types());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext cvc) {
        return (file != null && types.contains(file.getContentType()));
    }
}
