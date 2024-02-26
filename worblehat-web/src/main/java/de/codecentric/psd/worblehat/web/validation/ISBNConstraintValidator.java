package de.codecentric.psd.worblehat.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.ISBNValidator;

public class ISBNConstraintValidator implements ConstraintValidator<ISBN, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    // Don't validate null, empty and blank strings, since these are validated by @NotNull,
    // @NotEmpty and @NotBlank
    if (StringUtils.isNotBlank(value)) {
      return ISBNValidator.getInstance().isValidISBN10(value);
    }
    return true;
  }
}
