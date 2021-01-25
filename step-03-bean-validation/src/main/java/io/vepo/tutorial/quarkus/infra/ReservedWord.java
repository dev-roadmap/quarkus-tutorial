package io.vepo.tutorial.quarkus.infra;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static javax.validation.constraintvalidation.ValidationTarget.ANNOTATED_ELEMENT;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraintvalidation.SupportedValidationTarget;

@Documented
@Constraint(validatedBy = ReservedWordValidator.class)
@Target({
    METHOD,
    FIELD,
    ANNOTATION_TYPE,
    CONSTRUCTOR,
    PARAMETER,
    TYPE_USE })
@Retention(RUNTIME)
@Repeatable(ReservedWords.class)
@SupportedValidationTarget(ANNOTATED_ELEMENT)
@ReportAsSingleViolation
public @interface ReservedWord {
    String value();

    String message() default "You are using a Reserved Word";

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};
}
