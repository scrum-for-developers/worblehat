package de.codecentric.psd.worblehat.web.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ISBNConstraintValidatorTest {

  private ISBNConstraintValidator isbnConstraintValidator;

  private ConstraintValidatorContext constraintValidatorContext;

  @BeforeEach
  public void setUp() throws Exception {
    isbnConstraintValidator = new ISBNConstraintValidator();
    constraintValidatorContext = mock(ConstraintValidatorContext.class);
  }

  @Test
  public void initializeShouldTakeIsbn() throws Exception {
    ISBN isbn = mock(ISBN.class);
    isbnConstraintValidator.initialize(isbn);
  }

  @Test
  public void shouldReturnTrueIfBlank() throws Exception {
    boolean actual = isbnConstraintValidator.isValid("", constraintValidatorContext);
    assertTrue(actual);
  }

  @Test
  public void shouldReturnTrueIfValidISBN() throws Exception {
    boolean actual = isbnConstraintValidator.isValid("0132350882", constraintValidatorContext);
    assertTrue(actual);
  }

  @Test
  public void shouldReturnFalseIfInvalidISBN() throws Exception {
    boolean actual = isbnConstraintValidator.isValid("0123459789", constraintValidatorContext);
    assertFalse(actual);
  }
}
