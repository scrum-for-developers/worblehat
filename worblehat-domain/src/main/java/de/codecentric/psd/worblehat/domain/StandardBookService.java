package de.codecentric.psd.worblehat.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.time.temporal.ChronoUnit.DAYS;

/** The domain service class for book operations. */
@Service
@Transactional
public class StandardBookService implements BookService {

  @Autowired
  public StandardBookService(
    BorrowingRepository borrowingRepository, BookRepository bookRepository) {
    this.borrowingRepository = borrowingRepository;
    this.bookRepository = bookRepository;
  }

  private BorrowingRepository borrowingRepository;

  private BookRepository bookRepository;

  @Override
  public void returnAllBooksByBorrower(String borrowerEmailAddress) {
    List<Borrowing> borrowingsByUser =
      borrowingRepository.findBorrowingsByBorrower(borrowerEmailAddress);
    for (Borrowing borrowing : borrowingsByUser) {
      borrowingRepository.delete(borrowing);
    }
  }

  @Override
  public Optional<Borrowing> borrowBook(String isbn, String borrower) {
    Set<Book> books = bookRepository.findByIsbn(isbn);

    Optional<Book> unborrowedBook =
      books.stream().filter(book -> book.getBorrowing() == null).findFirst();

    return unborrowedBook.map(
      book -> {
        book.borrowNowByBorrower(borrower);
        borrowingRepository.save(book.getBorrowing());
        return book.getBorrowing();
      });
  }

  @Override
  public Set<Book> findBooksByIsbn(String isbn) {
    return bookRepository.findByIsbn(isbn);
  }

  @Override
  public List<Book> findAllBooks() {
    return bookRepository.findAllByOrderByTitle();
  }

  @Override
  public Optional<Book> createBook(
    @Nonnull String title,
    @Nonnull String author,
    @Nonnull String edition,
    @Nonnull String isbn,
    int yearOfPublication) {
    Book book = new Book(title, author, edition, isbn, yearOfPublication);

    Optional<Book> bookFromRepo = bookRepository.findTopByIsbn(isbn);

    if (!bookFromRepo.isPresent() || book.isSameCopy(bookFromRepo.get())) {
      return Optional.of(bookRepository.save(book));
    } else return Optional.empty();
  }

  @Override
  public Book updateBook(Book aBook) {
    return bookRepository.save(aBook);
  }

  @Override
  public boolean bookExists(String isbn) {
    Set<Book> books = bookRepository.findByIsbn(isbn);
    return !books.isEmpty();
  }

  @Override
  public void deleteAllBooks() {
    borrowingRepository.deleteAll();
    bookRepository.deleteAll();
  }

  @Override
  public long calculateFeeForBorrower(String borrowerEmail) {
    List<Borrowing> borrowings = borrowingRepository.findBorrowingsByBorrower(borrowerEmail);
    long fee = 0;
    for (Borrowing borrowing : borrowings){
      long days = DAYS.between(borrowing.getBorrowDate(), LocalDate.now());
      if (days > 28 && days <=35){
        fee = 1;
      } else if (days > 35 && days <= 42){
        fee = 2;
      } else if (days > 42){
        long additionalWeeks = ((days - 1) / 7) - 5;
        fee = 2 + (additionalWeeks * 3);
      }
    }
    return fee;
  }

}
