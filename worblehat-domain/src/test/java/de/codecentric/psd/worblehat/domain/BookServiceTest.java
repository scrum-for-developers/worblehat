package de.codecentric.psd.worblehat.domain;

import de.codecentric.psd.worblehat.domain.*;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class BookServiceTest {

	private BorrowingRepository borrowingRepository;

	private BookRepository bookRepository;

	private BookService bookService;

	private static final String BORROWER_EMAIL = "someone@codecentric.de";

	private static final DateTime NOW = DateTime.now();

	private static final Book TEST_BOOK = new Book("title", "author", "edition", "isbn", 2016);

	@Before
	public void setup() throws Exception {
		borrowingRepository = mock(BorrowingRepository.class);
		bookRepository = mock(BookRepository.class);
		bookService = new StandardBookService(borrowingRepository, bookRepository);
	}

	@Test
	public void shouldReturnAllBooksOfOnePerson() {
		Borrowing borrowing = new Borrowing(TEST_BOOK, BORROWER_EMAIL, NOW);
		List<Borrowing> result = Collections.singletonList(borrowing);
		when(borrowingRepository.findBorrowingsByBorrower(BORROWER_EMAIL))
		.thenReturn(result);
		bookService.returnAllBooksByBorrower(BORROWER_EMAIL);
		verify(borrowingRepository).delete(borrowing);
	}

	@Test
	public void shouldSaveBorrowingWithBorrowerEmail() throws Exception {
		when(borrowingRepository.findBorrowingForBook(TEST_BOOK)).thenReturn(null);
		ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
		bookService.borrowBook(TEST_BOOK, BORROWER_EMAIL);
		verify(borrowingRepository).save(borrowingArgumentCaptor.capture());
		assertThat(borrowingArgumentCaptor.getValue().getBorrowerEmailAddress(), is(BORROWER_EMAIL));
	}

	@Test(expected = BookAlreadyBorrowedException.class)
	public void shouldThrowExceptionWhenBookAlreadyBorrowed() throws Exception {
		when(borrowingRepository.findBorrowingForBook(TEST_BOOK)).thenReturn(new Borrowing(TEST_BOOK, BORROWER_EMAIL, NOW));
		bookService.borrowBook(TEST_BOOK, BORROWER_EMAIL);
	}

	@Test
	public void shouldCreateBook() throws Exception {
		ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
		bookService.createBook(TEST_BOOK.getTitle(), TEST_BOOK.getAuthor(), TEST_BOOK.getEdition(),
				TEST_BOOK.getIsbn(), TEST_BOOK.getYearOfPublication());
		verify(bookRepository).save(bookArgumentCaptor.capture());
		assertThat(bookArgumentCaptor.getValue().getTitle(), is(TEST_BOOK.getTitle()));
		assertThat(bookArgumentCaptor.getValue().getAuthor(), is(TEST_BOOK.getAuthor()));
		assertThat(bookArgumentCaptor.getValue().getEdition(), is(TEST_BOOK.getEdition()));
		assertThat(bookArgumentCaptor.getValue().getIsbn(), is(TEST_BOOK.getIsbn()));
		assertThat(bookArgumentCaptor.getValue().getYearOfPublication(), is(TEST_BOOK.getYearOfPublication()));
	}

	@Test
	public void shouldFindAllBooks() throws Exception {
		List<Book> expectedBooks = new ArrayList<>();
		expectedBooks.add(TEST_BOOK);
		when(bookRepository.findAllBooks()).thenReturn(expectedBooks);
		List<Book> actualBooks = bookService.findAllBooks();
		assertThat(actualBooks, is(expectedBooks));
	}

	@Test
	public void shouldFindexistingBooks() throws Exception {
		when(bookRepository.findBookByIsbn(TEST_BOOK.getIsbn())).thenReturn(TEST_BOOK);
		Book actualBook = bookRepository.findBookByIsbn(TEST_BOOK.getIsbn());
		assertThat(actualBook, is(TEST_BOOK));
	}

	@Test
	public void shouldVerifyExistingBooks() throws Exception {
		when(bookRepository.findBookByIsbn(TEST_BOOK.getIsbn())).thenReturn(TEST_BOOK);
		Boolean bookExists = bookService.bookExists(TEST_BOOK.getIsbn());
		assertTrue(bookExists);
	}

	@Test
	public void shouldDeleteAllBooksAndBorrowings() throws Exception {
		bookService.deleteAllBooks();
		verify(bookRepository).deleteAll();
		verify(borrowingRepository).deleteAll();
	}

	@Test
	public void shouldCalculateFeeForBorrower() throws Exception {
		int fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
		assertThat(fee, is(0));
	}

	@Test
	public void whenBookWasRented28DaysAgoFeeShouldBeZero() throws Exception {
		DateTime twentyEightDaysAgo = DateTime.now().minusDays(28);
		createBorrowingsWithDate(twentyEightDaysAgo);
		int fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
		assertThat(fee, is(0));
	}

	@Test
	public void whenBookWasRented29DaysAgoFeeShouldBeOne() throws Exception {
		DateTime twentyNineDaysAgo = DateTime.now().minusDays(29);
		createBorrowingsWithDate(twentyNineDaysAgo);
		int fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
		assertThat(fee, is(1));
	}

	@Test
	public void whenBookWasRented35DaysAgoFeeShouldBeOne() throws Exception {
		DateTime thirtyFiveDaysAgo = DateTime.now().minusDays(35);
		createBorrowingsWithDate(thirtyFiveDaysAgo);
		int fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
		assertThat(fee, is(1));
	}

	@Test
	public void whenBookWasRented36DaysAgoFeeShouldBeTwo() throws Exception {
		DateTime thirtySixDaysAgo = DateTime.now().minusDays(36);
		createBorrowingsWithDate(thirtySixDaysAgo);
		int fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
		assertThat(fee, is(2));
	}

	@Test
	public void whenBookWasRented42DaysAgoFeeShouldBeTwo() throws Exception {
		DateTime fortyTwoDaysAgo = DateTime.now().minusDays(42);
		createBorrowingsWithDate(fortyTwoDaysAgo);
		int fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
		assertThat(fee, is(2));
	}

	@Test
	public void whenBookWasRented43DaysAgoFeeShouldBeFive() throws Exception {
		DateTime fortyThreeDaysAgo = DateTime.now().minusDays(43);
		createBorrowingsWithDate(fortyThreeDaysAgo);
		int fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
		assertThat(fee, is(5));
	}

	@Test
	public void whenBookWasRented49DaysAgoFeeShouldBeFive() throws Exception {
		DateTime fortyNineDaysAgo = DateTime.now().minusDays(49);
		createBorrowingsWithDate(fortyNineDaysAgo);
		int fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
		assertThat(fee, is(5));
	}

	@Test
	public void whenBookWasRented50DaysAgoFeeShouldBeEight() throws Exception {
		DateTime fiftyDaysAgo = DateTime.now().minusDays(50);
		createBorrowingsWithDate(fiftyDaysAgo);
		int fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
		assertThat(fee, is(8));
	}

	@Test
	public void whenBookWasRented57DaysAgoFeeShouldBeEleven() throws Exception {
		DateTime fiftySevenDaysAgo = DateTime.now().minusDays(57);
		createBorrowingsWithDate(fiftySevenDaysAgo);
		int fee = bookService.calculateFeeForBorrower(BORROWER_EMAIL);
		assertThat(fee, is(11));
	}

	private void createBorrowingsWithDate(DateTime twentyEightDaysAgo) {
		final Borrowing borrowing = new Borrowing(TEST_BOOK, BORROWER_EMAIL, twentyEightDaysAgo);
		List<Borrowing> borrowings = new ArrayList<>();
		borrowings.add(borrowing);
		when(borrowingRepository.findBorrowingsByBorrower(BORROWER_EMAIL)).thenReturn(borrowings);
	}
}
