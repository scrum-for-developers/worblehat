package de.codecentric.psd.worblehat.acceptancetests.step.page;

import static de.codecentric.psd.worblehat.acceptancetests.step.StepUtilities.doWithEach;
import static org.assertj.core.api.Assertions.assertThat;

import de.codecentric.psd.worblehat.acceptancetests.adapter.SeleniumAdapter;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.HtmlBook;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.HtmlBookList;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.PageElement;
import de.codecentric.psd.worblehat.acceptancetests.step.StoryContext;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

public class BookList {

  @Autowired public StoryContext context;

  private SeleniumAdapter seleniumAdapter;

  @Autowired
  public BookList(SeleniumAdapter seleniumAdapter) {
    this.seleniumAdapter = seleniumAdapter;
  }

  @Then("the booklist contains a book with {string}, {string}, {string}, {int} and {string}")
  public void bookListContainsRowWithValues(
      final String title,
      final String author,
      final String year,
      final Integer edition,
      final String isbn) {
    seleniumAdapter.gotoPage(Page.BOOK_LIST);
    HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
    HtmlBook htmlBook = htmlBookList.getBookByIsbn(isbn);
    assertThat(title).isEqualTo(htmlBook.getTitle());
    assertThat(author).isEqualTo(htmlBook.getAuthor());
    assertThat(year).isEqualTo(htmlBook.getYearOfPublication());
    assertThat(edition).isEqualTo(htmlBook.getEdition());
    assertThat(isbn).isEqualTo(htmlBook.getIsbn());
  }

  @Then("the booklist shows that book with {string} as {string}")
  public void bookIsListedWithSpecificProperty(String property, String value) {
    seleniumAdapter.gotoPage(Page.BOOK_LIST);
    HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
    String isbn = context.get("LAST_INSERTED_BOOK_ISBN");
    HtmlBook htmlBook = htmlBookList.getBookByIsbn(isbn);
    switch (property) {
      case "title":
        assertThat(htmlBook.getTitle()).isEqualTo(value);
        break;
      case "author":
        assertThat(htmlBook.getAuthor()).isEqualTo(value);
        break;
      case "year":
        assertThat(htmlBook.getYearOfPublication()).isEqualTo(value);
        break;
      case "isbn":
        assertThat(htmlBook.getIsbn()).isEqualTo(value);
        break;
    }
  }

  @Then("The library contains no books")
  public void libraryIsEmpty() {
    seleniumAdapter.gotoPage(Page.BOOK_LIST);
    HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
    assertThat(htmlBookList.size()).isEqualTo(0);
  }

  @Then("the booklist lists {string} as borrower only for the book(s) with isbn(s) {string}")
  public void bookListHasBorrowerForBookWithIsbn(final String borrower, final String isbns) {
    seleniumAdapter.gotoPage(Page.BOOK_LIST);
    HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
    doWithEach(
        isbns,
        (isbn) -> {
          HtmlBook htmlBook = htmlBookList.getBookByIsbn(isbn);
          assertThat(htmlBook.getBorrower()).isEqualTo(borrower);
        });
  }

  @Then("books {string} are not borrowed anymore by borrower {string}")
  public void booksAreNotBorrowedByBorrower1(String isbns, String borrower) {
    seleniumAdapter.gotoPage(Page.BOOK_LIST);
    HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
    doWithEach(
        isbns,
        (isbn) -> assertThat(htmlBookList.getBookByIsbn(isbn).getBorrower()).isNullOrEmpty());
  }

  @Then("books {string} are still borrowed by borrower {string}")
  public void booksAreStillBorrowedByBorrower2(String isbns, String borrower2) {
    seleniumAdapter.gotoPage(Page.BOOK_LIST);
    HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
    doWithEach(
        isbns,
        (isbn) -> assertThat(htmlBookList.getBookByIsbn(isbn).getBorrower()).isEqualTo(borrower2));
  }
}
