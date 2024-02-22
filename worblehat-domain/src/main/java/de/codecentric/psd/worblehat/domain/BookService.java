package de.codecentric.psd.worblehat.domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/** The interface of the domain service for books. */
public interface BookService {

  Optional<Book> createBook(
      String title, String author, String edition, String isbn, int yearOfPublication);

  Book updateBook(Book aBook);

  boolean bookExists(String isbn);

  void deleteAllBooks();

  Optional<Borrowing> borrowBook(String isbn, String borrower);

  void returnAllBooksByBorrower(String string);

  List<Book> findAllBooks();

  Set<Book> findBooksByIsbn(String isbn);
}
