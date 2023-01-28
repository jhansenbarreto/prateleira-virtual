package com.prateleiravirtual.core.validation.validator;

import com.prateleiravirtual.core.validation.annotation.AnoValido;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Calendar;

/**
 * Classe que implementa a interface para validar restrições. Validador da
 * anotação @AnoValido. Restrição -> Um ano precisa estar entre um ano mínimo
 * informado no uso da anotação e o ano atual no momento da verificação.
 *
 * @author Jhansen Barreto
 */
public class AnoValidoValidator implements ConstraintValidator<AnoValido, Number> {

    private int min;
    private int anoAtual;

    @Override
    public void initialize(AnoValido constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.anoAtual = Calendar.getInstance().get(Calendar.YEAR);

        //EXCEÇÃO PARA CHAMAR A ATENÇÃO DO PROGRAMADOR EM CASO DE ANO MÍNIMO INFORMADO ERRONEAMENTE
        if (min > anoAtual) {
            throw new IllegalArgumentException("Não é possível definir um ano futuro como ano mínimo válido.");
        }
    }

    @Override
    public boolean isValid(Number ano, ConstraintValidatorContext context) {
        return ((ano != null) && (ano.intValue() >= min) && (ano.intValue() <= anoAtual));
    }
}
