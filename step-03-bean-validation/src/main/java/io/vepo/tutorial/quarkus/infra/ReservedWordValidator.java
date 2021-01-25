package io.vepo.tutorial.quarkus.infra;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReservedWordValidator implements ConstraintValidator<ReservedWord, String> {

    private String word;

    @Override
    public void initialize(ReservedWord wordAnnotation) {
        this.word = wordAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.compareToIgnoreCase(word) != 0;
    }

}
