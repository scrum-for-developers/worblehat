package de.codecentric.psd.worblehat.web.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ISBNConstraintValidatorTest {

  private ISBNConstraintValidator isbnConstraintValidator;

  private ConstraintValidatorContext constraintValidatorContext;

  @BeforeEach
  public void setUp() throws Exception {
    isbnConstraintValidator = new ISBNConstraintValidator();
    constraintValidatorContext = mock(ConstraintValidatorContext.class);
  }

  @Test
  void initializeShouldTakeIsbn() {
    ISBN isbn = mock(ISBN.class);
    assertDoesNotThrow(() -> isbnConstraintValidator.initialize(isbn));
  }

  @Test
  void shouldReturnTrueIfBlank() throws Exception {
    boolean actual = isbnConstraintValidator.isValid("", constraintValidatorContext);
    assertTrue(actual);
  }

  @Test
  void shouldReturnTrueIfValidISBN() throws Exception {
    boolean actual = isbnConstraintValidator.isValid("0132350882", constraintValidatorContext);
    assertTrue(actual);
  }

  @Test
  void shouldReturnFalseIfInvalidISBN() throws Exception {
    boolean actual = isbnConstraintValidator.isValid("0123459789", constraintValidatorContext);
    assertFalse(actual);
  }
}
