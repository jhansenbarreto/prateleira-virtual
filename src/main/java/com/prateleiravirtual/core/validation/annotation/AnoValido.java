package com.prateleiravirtual.core.validation.annotation;

import com.prateleiravirtual.core.validation.validator.AnoValidoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar um ano informado. É necessário informar um valor mínimo
 * no uso da anotação. Este valor será usado como base para a validação.
 *
 * @author Jhansen Barreto
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AnoValidoValidator.class})
public @interface AnoValido {

    public String message() default "Deve ser menor ou igual ao ano corrente";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    int min();
}
