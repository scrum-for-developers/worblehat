package de.codecentric.psd.worblehat.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/** Borrowing Entity */
@Entity
public class Borrowing implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id; // NOSONAR

  private String borrowerEmailAddress;
  private LocalDate borrowDate;
  @OneToOne() private Book borrowedBook;

  /**
   * @param book The borrowed book
   * @param borrowerEmailAddress The borrowers e-mail Address
   * @param borrowDate The borrow date
   */
  public Borrowing(Book book, String borrowerEmailAddress, LocalDate borrowDate) {
    super();
    this.borrowedBook = book;
    this.borrowerEmailAddress = borrowerEmailAddress;
    this.borrowDate = borrowDate;
  }

  public Borrowing(Book book, String borrowerEmailAddress) {
    this(book, borrowerEmailAddress, LocalDate.now());
  }

  /** Empty constructor needed by Hibernate. */
  private Borrowing() {
    super();
  }

  public LocalDate getBorrowDate() {
    return borrowDate;
  }

  public String getBorrowerEmailAddress() {
    return borrowerEmailAddress;
  }

  public Book getBorrowedBook() {
    return borrowedBook;
  }

  @Override
  public String toString() {
    return "Borrowing{"
        + "borrowerEmailAddress='"
        + borrowerEmailAddress
        + '\''
        + ", borrowDate="
        + borrowDate
        + '}';
  }
}
