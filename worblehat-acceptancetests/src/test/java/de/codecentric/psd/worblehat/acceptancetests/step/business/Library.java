package de.codecentric.psd.worblehat.acceptancetests.step.business;

import static de.codecentric.psd.worblehat.acceptancetests.step.StepUtilities.doWithEach;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.Splitter;
import de.codecentric.psd.worblehat.acceptancetests.step.StoryContext;
import de.codecentric.psd.worblehat.domain.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

// @Component("Library")
public class Library {

  @Autowired private BookService bookService;

  @Autowired private BorrowingRepository borrowingRepository;

  @Autowired private BookRepository bookRepository;

  @Autowired private final StoryContext storyContext;

  public Library(StoryContext storyContext) {
    this.storyContext = storyContext;
  }

  /*  @Autowired
    public Library(ApplicationContext applicationContext) {
      this.bookService = applicationContext.getBean(BookService.class);
    }
  */
  // *******************
  // *** G I V E N *****
  // *******************

  @Given("an empty library")
  public void emptyLibrary() {
    bookService.deleteAllBooks();
  }

  @Given("a library, containing only (one )book(s) with isbn(s) {string}")
  public void createLibraryWithSingleBookWithGivenIsbn(String isbns) {
    emptyLibrary();
    List<Book> newBooks = createNewBooksByISBNS(isbns);
    newBooks.stream().forEach(book -> storyContext.putObject("LAST_INSERTED_BOOK", book));
    storyContext.putObject("LAST_INSERTED_BOOKS", newBooks);
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

  @Given("{string} has borrowed book(s) {string} on {date}")
  public void has_borrowed_books_on(String borrower, String isbns, LocalDate borrowDate) {
    List<Book> books = (List<Book>) storyContext.getObject("LAST_INSERTED_BOOKS");
    Splitter.on(" ").omitEmptyStrings().splitToList(isbns).stream()
        .forEach(
            isbn -> {
              Book bookToBeBorrowed =
                  books.stream()
                      .filter(book -> book.getIsbn().equals(isbn))
                      .findFirst()
                      .orElseThrow();

              LocalDate date = borrowDate.atStartOfDay(ZoneId.systemDefault()).toLocalDate();

              Borrowing newBorrowing = new Borrowing(bookToBeBorrowed, borrower, date);
              borrowingRepository.save(newBorrowing);

              bookToBeBorrowed.setBorrowing(newBorrowing);
              bookRepository.save(bookToBeBorrowed);
            });
  }

  private List<Book> createNewBooksByISBNS(String isbns) {
    List<Book> books =
        Splitter.on(" ").omitEmptyStrings().splitToList(isbns).stream()
            .map(
                isbn -> {
                  Book book = DemoBookFactory.createDemoBook().withISBN(isbn).build();
                  Book newBook =
                      bookService
                          .createBook(
                              book.getTitle(),
                              book.getAuthor(),
                              book.getEdition(),
                              book.getIsbn(),
                              book.getYearOfPublication())
                          .orElseThrow(IllegalStateException::new);
                  return newBook;
                })
            .collect(Collectors.toList());
    return books;
  }

  // *****************
  // *** W H E N *****
  // *****************

  // *****************
  // *** T H E N *****
  // *****************

  @Then("the library contains {int} copies of the book with {string}")
  public void shouldContainCopiesOfBook(int nrOfCopies, String isbn) {
    waitForServerResponse();
    Set<Book> books = bookService.findBooksByIsbn(isbn);
    assertThat(books).hasSize(nrOfCopies);
    assertThat(books).extracting(Book::getIsbn).containsOnly(isbn);
  }

  private void waitForServerResponse() {
    // normally you would have much better mechanisms for waiting for a
    // server response. We are choosing a simple solution for the sake of this
    // training
    try {
      Thread.sleep(100); // NOSONAR
    } catch (InterruptedException e) {
      // pass
    }
  }
}
