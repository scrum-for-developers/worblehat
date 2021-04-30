package de.codecentric.psd.worblehat.web.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NumericConstraintValidatorTest {

  ConstraintValidatorContext constraintValidatorContext;
  private NumericConstraintValidator numericConstraintValidator;

  @BeforeEach
  void setUp() throws Exception {
    numericConstraintValidator = new NumericConstraintValidator();
    constraintValidatorContext = mock(ConstraintValidatorContext.class);
  }

  @Test
  void initializeShouldTakeNumeric() {
    Numeric numeric = mock(Numeric.class);
    assertDoesNotThrow(() -> numericConstraintValidator.initialize(numeric));
  }

  @Test
  void shouldReturnTrueIfBlank() throws Exception {
    boolean actual = numericConstraintValidator.isValid("", constraintValidatorContext);
    assertTrue(actual);
  }

  @Test
  void shouldReturnTrueIfNumeric() throws Exception {
    boolean actual = numericConstraintValidator.isValid("1", constraintValidatorContext);
    assertTrue(actual);
  }

  @Test
  void shouldReturnFalsIfNotNumeric() throws Exception {
    boolean actual = numericConstraintValidator.isValid("x", constraintValidatorContext);
    assertFalse(actual);
  }
}
