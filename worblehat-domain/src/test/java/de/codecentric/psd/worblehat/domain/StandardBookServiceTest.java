package de.codecentric.psd.worblehat.domain;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

class StandardBookServiceTest {

  @Mock private BorrowingRepository borrowingRepository;

  @Mock private BookRepository bookRepository;

  private BookService bookService;

  private static final String BORROWER_EMAIL = "someone@codecentric.de";

  private static final LocalDate NOW = LocalDate.now();

  private Book aBook, aCopyofBook, anotherBook;
  private Book aBorrowedBook, aCopyofBorrowedBook, anotherBorrowedBook;
  private Borrowing aBorrowing, aBorrowingOfCopy, anotherBorrowing;

  @BeforeEach
  void setup() {
    initMocks(this);

    aBook = new Book("title", "author", "edition", "isbn", 2016);
    aCopyofBook = new Book("title", "author", "edition", "isbn", 2016);
    anotherBook = new Book("title2", "author2", "edition2", "isbn2", 2016);

    aBorrowedBook = new Book("title", "author", "edition", "isbn", 2016);
    aBorrowing = new Borrowing(aBorrowedBook, BORROWER_EMAIL, NOW);
    aBorrowedBook.borrowNowByBorrower(BORROWER_EMAIL);

    aCopyofBorrowedBook = new Book("title", "author", "edition", "isbn", 2016);
    aBorrowingOfCopy = new Borrowing(aCopyofBorrowedBook, BORROWER_EMAIL, NOW);
    aCopyofBorrowedBook.borrowNowByBorrower(BORROWER_EMAIL);

    anotherBorrowedBook = new Book("title2", "author2", "edition2", "isbn2", 2016);
    anotherBorrowing = new Borrowing(anotherBorrowedBook, BORROWER_EMAIL, NOW);
    anotherBorrowedBook.borrowNowByBorrower(BORROWER_EMAIL);

    when(borrowingRepository.findBorrowingsByBorrower(BORROWER_EMAIL))
        .thenReturn(Arrays.asList(aBorrowing, anotherBorrowing));

    when(borrowingRepository.findBorrowingForBook(aBook)).thenReturn(null);

    bookService = new StandardBookService(borrowingRepository, bookRepository);
  }

  private void givenALibraryWith(Book... books) {
    Map<String, Set<Book>> bookCopies = new HashMap<>();
    for (Book book : books) {
      if (!bookCopies.containsKey(book.getIsbn())) {
        bookCopies.put(book.getIsbn(), new HashSet<>());
      }
      bookCopies.get(book.getIsbn()).add(book);
    }
    // in case save is called on the repository, it should return something meaningful instead of
    // null
    when(bookRepository.save(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());
    for (Map.Entry<String, Set<Book>> entry : bookCopies.entrySet()) {
      when(bookRepository.findByIsbn(entry.getKey())).thenReturn(entry.getValue());
      when(bookRepository.findTopByIsbn(entry.getKey()))
          .thenReturn(Optional.of(entry.getValue().iterator().next()));
    }
  }

  @Test
  void shouldReturnAllBooksOfOnePerson() {
    bookService.returnAllBooksByBorrower(BORROWER_EMAIL);
    verify(borrowingRepository).delete(anotherBorrowing);
  }

  @Test
  void shouldSaveBorrowingWithBorrowerEmail() {
    givenALibraryWith(aBook);
    ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
    bookService.borrowBook(aBook.getIsbn(), BORROWER_EMAIL);
    verify(borrowingRepository).save(borrowingArgumentCaptor.capture());
    assertThat(
        borrowingArgumentCaptor.getValue().getBorrowerEmailAddress(), equalTo(BORROWER_EMAIL));
  }

  @Test()
  void shouldNotBorrowWhenBookAlreadyBorrowed() {
    givenALibraryWith(aBorrowedBook);
    Optional<Borrowing> borrowing = bookService.borrowBook(aBorrowedBook.getIsbn(), BORROWER_EMAIL);
    assertTrue(!borrowing.isPresent());
  }

  @Test
  void shouldSelectOneOfTwoBooksWhenBothAreNotBorrowed() {
    givenALibraryWith(aBook, aCopyofBook);
    ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
    bookService.borrowBook(aBook.getIsbn(), BORROWER_EMAIL);
    verify(borrowingRepository).save(borrowingArgumentCaptor.capture());
    assertThat(borrowingArgumentCaptor.getValue().getBorrowerEmailAddress(), is(BORROWER_EMAIL));
    assertThat(
        borrowingArgumentCaptor.getValue().getBorrowedBook(),
        either(is(aBook)).or(is(aCopyofBook)));
  }

  @Test
  void shouldSelectUnborrowedOfTwoBooksWhenOneIsBorrowed() {
    givenALibraryWith(aBook, aBorrowedBook);
    ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
    bookService.borrowBook(aBook.getIsbn(), BORROWER_EMAIL);
    verify(borrowingRepository).save(borrowingArgumentCaptor.capture());
    assertThat(borrowingArgumentCaptor.getValue().getBorrowerEmailAddress(), is(BORROWER_EMAIL));
    assertThat(borrowingArgumentCaptor.getValue().getBorrowedBook(), is(aBook));
  }

  @Test
  void shouldThrowExceptionWhenAllBooksAreBorrowedRightNow() {
    givenALibraryWith(aBorrowedBook, aCopyofBorrowedBook);
    ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
    Optional<Borrowing> borrowing = bookService.borrowBook(aBorrowedBook.getIsbn(), BORROWER_EMAIL);
    assertThat(borrowing, isEmpty());
    verify(borrowingRepository, never()).save(any(Borrowing.class));
  }

  @Test
  void shouldCreateBook() {
    when(bookRepository.save(any(Book.class))).thenReturn(aBook);
    bookService.createBook(
        aBook.getTitle(),
        aBook.getAuthor(),
        aBook.getEdition(),
        aBook.getIsbn(),
        aBook.getYearOfPublication());

    // assert that book was saved to repository
    ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
    verify(bookRepository).save(bookArgumentCaptor.capture());

    // assert that the information was passed correctly to create the book
    assertThat(bookArgumentCaptor.getValue().getTitle(), is(aBook.getTitle()));
    assertThat(bookArgumentCaptor.getValue().getAuthor(), is(aBook.getAuthor()));
    assertThat(bookArgumentCaptor.getValue().getEdition(), is(aBook.getEdition()));
    assertThat(bookArgumentCaptor.getValue().getIsbn(), is(aBook.getIsbn()));
    assertThat(
        bookArgumentCaptor.getValue().getYearOfPublication(), is(aBook.getYearOfPublication()));
  }

  @Test
  void shouldCreateAnotherCopyOfExistingBook() {
    when(bookRepository.save(any(Book.class))).thenReturn(aBook);
    bookService.createBook(
        aBook.getTitle(),
        aBook.getAuthor(),
        aBook.getEdition(),
        aBook.getIsbn(),
        aBook.getYearOfPublication());
    verify(bookRepository, times(1)).save(any(Book.class));
  }

  @Test
  void shouldNotCreateAnotherCopyOfExistingBookWithDifferentTitle() {
    givenALibraryWith(aBook);
    bookService.createBook(
        aBook.getTitle() + "X",
        aBook.getAuthor(),
        aBook.getEdition(),
        aBook.getIsbn(),
        aBook.getYearOfPublication());
    verify(bookRepository, times(0)).save(any(Book.class));
  }

  @Test
  void shouldNotCreateAnotherCopyOfExistingBookWithDifferentAuthor() {
    givenALibraryWith(aBook);
    bookService.createBook(
        aBook.getTitle(),
        aBook.getAuthor() + "X",
        aBook.getEdition(),
        aBook.getIsbn(),
        aBook.getYearOfPublication());
    verify(bookRepository, times(0)).save(any(Book.class));
  }

  @Test
  void shouldFindAllBooks() {
    List<Book> expectedBooks = new ArrayList<>();
    expectedBooks.add(aBook);
    when(bookRepository.findAllByOrderByTitle()).thenReturn(expectedBooks);
    List<Book> actualBooks = bookService.findAllBooks();
    assertThat(actualBooks, is(expectedBooks));
  }

  @Test
  void shouldVerifyExistingBooks() {
    when(bookRepository.findByIsbn(aBook.getIsbn())).thenReturn(Collections.singleton(aBook));
    Boolean bookExists = bookService.bookExists(aBook.getIsbn());
    assertTrue(bookExists);
  }

  @Test
  void shouldVerifyNonexistingBooks() {
    when(bookRepository.findByIsbn(aBook.getIsbn())).thenReturn(Collections.emptySet());
    Boolean bookExists = bookService.bookExists(aBook.getIsbn());
    assertThat(bookExists, is(false));
  }

  @Test
  void shouldFindAllCopiesOfABook() {
    when(bookRepository.findByIsbn("isbn")).thenReturn(Set.of(aBook, aCopyofBook));
    Set<Book> booksByIsbn = bookService.findBooksByIsbn("isbn");
    assertThat(booksByIsbn, everyItem(hasProperty("isbn", is("isbn"))));
  }

  @Test
  void shouldDeleteAllBooksAndBorrowings() {
    bookService.deleteAllBooks();
    verify(bookRepository).deleteAll();
    verify(borrowingRepository).deleteAll();
  }

  @Test
  void shouldSaveUpdatedBookToRepo() {
    bookService.updateBook(aBook);
    verify(bookRepository).save(aBook);
  }
}
