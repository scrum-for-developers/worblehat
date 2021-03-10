package de.codecentric.psd.worblehat.acceptancetests.step.business;

import static de.codecentric.psd.worblehat.acceptancetests.step.StepUtilities.doWithEach;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

// @Component("Library")
public class Library {

  @Autowired(required = true)
  private BookService bookService;

  @Autowired
  public Library(ApplicationContext applicationContext) {
    this.bookService = applicationContext.getBean(BookService.class);
  }

  // *******************
  // *** G I V E N *****
  // *******************

  @Given("an empty library")
  public void emptyLibrary() {
    bookService.deleteAllBooks();
  }

  @Given("a library, containing only (a )book(s) with isbn(s) {string}")
  public void createLibraryWithSingleBookWithGivenIsbn(String isbns) {
    bookService.deleteAllBooks();
    doWithEach(
        isbns,
        (isbn) -> {
          Book book = DemoBookFactory.createDemoBook().withISBN(isbn).build();
          bookService
              .createBook(
                  book.getTitle(),
                  book.getAuthor(),
                  book.getEdition(),
                  isbn,
                  book.getYearOfPublication())
              .orElseThrow(IllegalStateException::new);
        });
  }

  @Given("{string} has borrowed books {string}")
  public void borrowerHasBorrowerdBooks(String borrower, String isbns) {
    doWithEach(
        isbns,
        (isbn) -> {
          Book book = DemoBookFactory.createDemoBook().withISBN(isbn).build();
          bookService
              .createBook(
                  book.getTitle(),
                  book.getAuthor(),
                  book.getEdition(),
                  isbn,
                  book.getYearOfPublication())
              .orElseThrow(IllegalStateException::new);

          bookService.borrowBook(book.getIsbn(), borrower);
        });
  }

  // *****************
  // *** W H E N *****
  // *****************

  // *****************
  // *** T H E N *****
  // *****************

  @Then("the library contains {int} copies of the book with {string}")
  public void shouldContainCopiesOfBook(Integer nrOfCopies, String isbn) {
    waitForServerResponse();
    Set<Book> books = bookService.findBooksByIsbn(isbn);
    assertThat(books.size(), is(nrOfCopies));
    assertThat(books, everyItem(hasProperty("isbn", is(isbn))));
  }

  private void waitForServerResponse() {
    // normally you would have much better mechanisms for waiting for a
    // server response. We are choosing a simple solution for the sake of this
    // training
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      // pass
    }
  }
}
