package de.codecentric.psd.worblehat.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.domain.Borrowing;
import de.codecentric.psd.worblehat.web.formdata.BookBorrowFormData;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

class BorrowBookControllerTest {

  private BookService bookService;

  private BorrowBookController borrowBookController;

  private BindingResult bindingResult;

  private BookBorrowFormData bookBorrowFormData;

  private static final Book TEST_BOOK = new Book("title", "author", "edition", "isbn", 2016);

  public static final String BORROWER_EMAIL = "someone@codecentric.de";

  @BeforeEach
  void setUp() {
    bookService = mock(BookService.class);
    bindingResult = new MapBindingResult(new HashMap<>(), "");
    bookBorrowFormData = new BookBorrowFormData();
    borrowBookController = new BorrowBookController(bookService);
  }

  @Test
  void shouldSetupForm() {
    ModelMap modelMap = new ModelMap();

    borrowBookController.setupForm(modelMap);

    assertThat(modelMap.get("borrowFormData")).isNotNull();
  }

  @Test
  void shouldNavigateToBorrowWhenResultHasErrors() {
    bindingResult.addError(new ObjectError("", ""));

    String navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult);

    assertThat(navigateTo).isEqualTo("borrow");
  }

  @Test
  void shouldRejectBorrowingIfBookDoesNotExist() {
    when(bookService.findBooksByIsbn(TEST_BOOK.getIsbn())).thenReturn(null);

    String navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult);

    assertThat(bindingResult.hasFieldErrors("isbn")).isEqualTo(true);
    assertThat(navigateTo).isEqualTo("borrow");
  }

  @Test
  void shouldRejectAlreadyBorrowedBooks() {
    bookBorrowFormData.setEmail(BORROWER_EMAIL);
    bookBorrowFormData.setIsbn(TEST_BOOK.getIsbn());
    when(bookService.findBooksByIsbn(TEST_BOOK.getIsbn()))
        .thenReturn(Collections.singleton(TEST_BOOK));
    String navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult);

    assertThat(bindingResult.hasFieldErrors("isbn")).isEqualTo(true);
    assertThat(bindingResult.getFieldError("isbn").getCode()).isEqualTo("noBorrowableBooks");
    assertThat(navigateTo).isEqualTo("borrow");
  }

  @Test
  void shouldNavigateHomeOnSuccess() {
    bookBorrowFormData.setEmail(BORROWER_EMAIL);
    bookBorrowFormData.setIsbn(TEST_BOOK.getIsbn());
    when(bookService.findBooksByIsbn(TEST_BOOK.getIsbn()))
        .thenReturn(Collections.singleton(TEST_BOOK));
    when(bookService.borrowBook(any(), any()))
        .thenReturn(Optional.of(new Borrowing(TEST_BOOK, BORROWER_EMAIL)));

    String navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult);
    verify(bookService).borrowBook(TEST_BOOK.getIsbn(), BORROWER_EMAIL);
    assertThat(navigateTo).isEqualTo("home");
  }

  @Test
  void shouldNavigateToHomeOnErrors() {
    String navigateTo =
        borrowBookController.handleErrors(new Exception(), new MockHttpServletRequest());

    assertThat(navigateTo).isEqualTo("home");
  }
}
