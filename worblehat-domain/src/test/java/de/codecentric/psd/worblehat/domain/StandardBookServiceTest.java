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

  @Test
  public void shouldCalculateFeeForBorrower() throws Exception {
    long fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
    assertThat(fee, is(0L));
  }

  @Test
  public void whenBookWasRented28DaysAgoFeeShouldBeZero() throws Exception {
    LocalDate twentyEightDaysAgo = LocalDate.now().minusDays(28);
    createBorrowingsWithDate(twentyEightDaysAgo);
    long fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
    assertThat(fee, is(0L));
  }

  @Test
  public void whenBookWasRented29DaysAgoFeeShouldBeOne() throws Exception {
    LocalDate twentyNineDaysAgo = LocalDate.now().minusDays(29);
    createBorrowingsWithDate(twentyNineDaysAgo);
    long fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
    assertThat(fee, is(1L));
  }

  @Test
  public void whenBookWasRented35DaysAgoFeeShouldBeOne() throws Exception {
    LocalDate thirtyFiveDaysAgo = LocalDate.now().minusDays(35);
    createBorrowingsWithDate(thirtyFiveDaysAgo);
    long fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
    assertThat(fee, is(1L));
  }

  @Test
  public void whenBookWasRented36DaysAgoFeeShouldBeTwo() throws Exception {
    LocalDate thirtySixDaysAgo = LocalDate.now().minusDays(36);
    createBorrowingsWithDate(thirtySixDaysAgo);
    long fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
    assertThat(fee, is(2L));
  }

  @Test
  public void whenBookWasRented42DaysAgoFeeShouldBeTwo() throws Exception {
    LocalDate fortyTwoDaysAgo = LocalDate.now().minusDays(42);
    createBorrowingsWithDate(fortyTwoDaysAgo);
    long fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
    assertThat(fee, is(2L));
  }

  @Test
  public void whenBookWasRented43DaysAgoFeeShouldBeFive() throws Exception {
    LocalDate fortyThreeDaysAgo = LocalDate.now().minusDays(43);
    createBorrowingsWithDate(fortyThreeDaysAgo);
    long fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
    assertThat(fee, is(5L));
  }

  @Test
  public void whenBookWasRented49DaysAgoFeeShouldBeFive() throws Exception {
    LocalDate fortyNineDaysAgo = LocalDate.now().minusDays(49);
    createBorrowingsWithDate(fortyNineDaysAgo);
    long fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
    assertThat(fee, is(5L));
  }

  @Test
  public void whenBookWasRented50DaysAgoFeeShouldBeEight() throws Exception {
    LocalDate fiftyDaysAgo = LocalDate.now().minusDays(50);
    createBorrowingsWithDate(fiftyDaysAgo);
    long fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
    assertThat(fee, is(8L));
  }

  @Test
  public void whenBookWasRented57DaysAgoFeeShouldBeEleven() throws Exception {
    LocalDate fiftySevenDaysAgo = LocalDate.now().minusDays(57);
    createBorrowingsWithDate(fiftySevenDaysAgo);
    long fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
    assertThat(fee, is(11L));
  }

  private void createBorrowingsWithDate(LocalDate twentyEightDaysAgo) {
    final Borrowing borrowing = new Borrowing(aBook, BORROWER_EMAIL, twentyEightDaysAgo);
    List<Borrowing> borrowings = new ArrayList<>();
    borrowings.add(borrowing);
    when(borrowingRepository.findBorrowingsByBorrower(BORROWER_EMAIL)).thenReturn(borrowings);
  }
}
