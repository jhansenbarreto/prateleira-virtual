package com.prateleiravirtual.core.validation.validator;

import com.prateleiravirtual.core.validation.annotation.FileSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

/**
 * Classe que implementa a interface para validar restrições. Validador da
 * anotação @FileSize. Restrição -> O tamanho de um arquivo deve ser menor ou
 * igual a um tamanho máximo informado no uso da anotação.
 *
 * @author Jhansen Barreto
 */
public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private DataSize size;

    @Override
    public void initialize(FileSize constraintAnnotation) {
        this.size = DataSize.parse(constraintAnnotation.max());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext cvc) {
        return (file != null && file.getSize() <= size.toBytes());
    }
}
