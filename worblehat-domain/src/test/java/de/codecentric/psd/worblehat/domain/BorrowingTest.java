package de.codecentric.psd.worblehat.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
    assertThat(borrowing.getBorrowDate(), is(LocalDate.now()));
  }

  @Test
  void shouldReturnBook() {
    assertThat(borrowing.getBorrowedBook(), is(aBook));
  }

  @Test
  void shouldReturnBorrower() {
    assertThat(borrowing.getBorrowerEmailAddress(), is("user@domain.com"));
  }
}
