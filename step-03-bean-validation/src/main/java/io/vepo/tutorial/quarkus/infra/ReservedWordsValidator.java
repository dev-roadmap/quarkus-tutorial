package io.vepo.tutorial.quarkus.infra;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReservedWordsValidator implements ConstraintValidator<ReservedWords, String> {
    private List<String> words;

    @Override
    public void initialize(ReservedWords wordsAnnotation) {
        words = asList(wordsAnnotation.value()).stream()
                                               .map(ReservedWord::value)
                                               .collect(toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return words.stream().noneMatch(word -> word.compareToIgnoreCase(value) == 0);
    }

}
