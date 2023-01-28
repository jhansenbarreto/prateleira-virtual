package com.prateleiravirtual.core.validation.annotation;

import com.prateleiravirtual.core.validation.validator.FileSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar o tamanho de um arquivo. É necessário informar um
 * tamanho máximo no uso da anotação. Este valor será usado como base para a
 * validação. Exemplo: 2MB.
 *
 * @author Jhansen Barreto
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FileSizeValidator.class})
public @interface FileSize {

    public String message() default "Tamanho máximo excedido.";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    String max();
}
