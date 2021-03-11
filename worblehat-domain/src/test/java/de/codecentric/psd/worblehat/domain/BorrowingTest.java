package de.codecentric.psd.worblehat.domain;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;

class BorrowingTest {

  @Mock
  Book aBook;
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
