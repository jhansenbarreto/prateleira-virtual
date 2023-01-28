package com.prateleiravirtual.core.validation.annotation;

import com.prateleiravirtual.core.validation.validator.FileContentTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar a extensão de um arquivo. É necessário informar um
 * array com os tipos válidos no uso da anotação. Exemplos: {image/jpeg,
 * image/png}.
 *
 * @author Jhansen Barreto
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FileContentTypeValidator.class})
public @interface FileContentType {

    public String message() default "Tipo de arquivo inválido.";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    String[] types();
}
