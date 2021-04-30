package de.codecentric.psd.worblehat.web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.BookDataFormData;
import java.util.HashMap;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

class InsertBookControllerTest {

  private InsertBookController insertBookController;

  private BookService bookService;

  private BookDataFormData bookDataFormData;

  private BindingResult bindingResult;

  private static final Book TEST_BOOK = new Book("title", "author", "edition", "isbn", 2016);

  @BeforeEach
  public void setUp() {
    bookService = mock(BookService.class);
    insertBookController = new InsertBookController(bookService);
    bookDataFormData = new BookDataFormData();
    bindingResult = new MapBindingResult(new HashMap<>(), "");
  }

  @Test
  void shouldSetupForm() {
    ModelMap modelMap = new ModelMap();

    insertBookController.setupForm(modelMap);

    assertThat(modelMap.get("bookDataFormData"), is(not(nullValue())));
  }

  @Test
  void shouldRejectErrors() {
    bindingResult.addError(new ObjectError("", ""));

    String navigateTo = insertBookController.processSubmit(bookDataFormData, bindingResult);

    assertThat(navigateTo, is("insertBooks"));
  }

  @Test
  void shouldCreateBookAndNavigateToBookList() {
    setupFormData();
    when(bookService.createBook(any(), any(), any(), any(), anyInt()))
        .thenReturn(Optional.of(TEST_BOOK));

    String navigateTo = insertBookController.processSubmit(bookDataFormData, bindingResult);

    verifyBookIsCreated();
    assertThat(navigateTo, is("redirect:bookList"));
  }

  @Test
  void shouldStayOnInsertBookPageWhenCreatingBookFails() {
    setupFormData();
    when(bookService.createBook(any(), any(), any(), any(), anyInt())).thenReturn(Optional.empty());

    String navigateTo = insertBookController.processSubmit(bookDataFormData, bindingResult);

    verifyBookIsCreated();
    assertThat(
        bindingResult.getGlobalErrors(),
        hasItem(hasProperty("codes", hasItemInArray("duplicateIsbn"))));
    assertThat(navigateTo, is("insertBooks"));
  }

  private void verifyBookIsCreated() {
    verify(bookService)
        .createBook(
            TEST_BOOK.getTitle(),
            TEST_BOOK.getAuthor(),
            TEST_BOOK.getEdition(),
            TEST_BOOK.getIsbn(),
            TEST_BOOK.getYearOfPublication());
  }

  private void setupFormData() {
    bookDataFormData.setTitle(TEST_BOOK.getTitle());
    bookDataFormData.setAuthor(TEST_BOOK.getAuthor());
    bookDataFormData.setEdition(TEST_BOOK.getEdition());
    bookDataFormData.setIsbn(TEST_BOOK.getIsbn());
    bookDataFormData.setYearOfPublication(String.valueOf(TEST_BOOK.getYearOfPublication()));
  }
}
