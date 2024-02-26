package de.codecentric.psd.worblehat.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class NumericConstraintValidator implements ConstraintValidator<Numeric, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    // Don't validate null, empty and blank strings, since these are validated by @NotNull,
    // @NotEmpty and @NotBlank
    if (StringUtils.isNotBlank(value)) {
      return StringUtils.isNumeric(value);
    }
    return true;
  }
}
