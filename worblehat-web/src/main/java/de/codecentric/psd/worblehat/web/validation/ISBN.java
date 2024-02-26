package de.codecentric.psd.worblehat.web.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ISBNConstraintValidator.class)
@Documented
public @interface ISBN {

  String message() default "{de.codecentric.psd.worblehat.web.validation.ISBN}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
