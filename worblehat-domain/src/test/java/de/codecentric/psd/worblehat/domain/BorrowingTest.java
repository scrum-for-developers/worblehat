package de.codecentric.psd.worblehat.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class BorrowingTest {

  @Mock Book aBook;
  private Borrowing borrowing;

  @BeforeEach
  void setUp() {
    initMocks(this);
    borrowing = new Borrowing(aBook, "user@domain.com");
  }

  @Test
  void shouldCreateNewBorrowingForToday() {
    assertThat(borrowing.getBorrowDate()).isEqualTo(LocalDate.now());
  }

  @Test
  void shouldReturnBook() {
    assertThat(borrowing.getBorrowedBook()).isEqualTo(aBook);
  }

  @Test
  void shouldReturnBorrower() {
    assertThat(borrowing.getBorrowerEmailAddress()).isEqualTo("user@domain.com");
  }

  @Test
  void shouldReturnRelevatInfoInToString() {
    String borrowingAsString = borrowing.toString();
    assertThat(borrowingAsString)
        .contains("borrowerEmailAddress")
        .contains("borrowDate")
        .contains("user@domain.com");
  }
}
