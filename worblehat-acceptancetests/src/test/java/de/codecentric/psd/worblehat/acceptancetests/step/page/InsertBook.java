package de.codecentric.psd.worblehat.acceptancetests.step.page;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import de.codecentric.psd.worblehat.acceptancetests.adapter.SeleniumAdapter;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.PageElement;
import de.codecentric.psd.worblehat.acceptancetests.step.StoryContext;
import de.codecentric.psd.worblehat.acceptancetests.step.business.DemoBookFactory;
import de.codecentric.psd.worblehat.domain.Book;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class InsertBook {

  private SeleniumAdapter seleniumAdapter;

  @Autowired public StoryContext context;

  @Autowired
  public InsertBook(SeleniumAdapter seleniumAdapter) {
    this.seleniumAdapter = seleniumAdapter;
  }

  // *******************
  // *** G I V E N *****
  // *******************

  // *****************
  // *** W H E N *****
  // *****************

  @When("a librarian adds a/another book with {string}, {string}, {int}, {string} and {string}")
  public void whenABookWithISBNisbnIsAdded(
      String title, String author, int edition, String year, String isbn) {
    seleniumAdapter.gotoPage(Page.INSERTBOOKS);
    fillInsertBookForm(title, author, edition, isbn, year);
    seleniumAdapter.clickOnPageElement(PageElement.ADDBOOKBUTTON);
    context.put("LAST_INSERTED_BOOK_ISBN", isbn);
  }

  @When("a librarian adds a random book and the {string} of that book is {string}")
  public void addRandomBookWithProperty(String property, String value) {
    seleniumAdapter.gotoPage(Page.INSERTBOOKS);
    DemoBookFactory randomBookBuilder = DemoBookFactory.createDemoBook();
    switch (property) {
      case "title":
        randomBookBuilder.withTitle(value);
        break;
      case "author":
        randomBookBuilder.withAuthor(value);
        break;
      case "year":
        randomBookBuilder.withYearOfPublication(value);
        break;
      case "isbn":
        randomBookBuilder.withISBN(value);
        break;
    }
    Book randomBook = randomBookBuilder.build();
    fillInsertBookForm(randomBook);
    seleniumAdapter.clickOnPageElement(PageElement.ADDBOOKBUTTON);
    context.put("LAST_INSERTED_BOOK_ISBN", randomBook.getIsbn());
  }

  // *****************
  // *** T H E N *****
  // *****************
  @Then("the page contains error message for field {string}")
  public void pageContainsErrorMessage(String field) {
    String errorMessage = seleniumAdapter.getTextFromElement(PageElement.errorFor(field));
    assertThat(errorMessage, notNullValue());
  }
  // *****************
  // *** U T I L *****
  // *****************

  private void fillInsertBookForm(Book aBook) {
    fillInsertBookForm(
        aBook.getTitle(),
        aBook.getAuthor(),
        Integer.parseInt(aBook.getEdition()),
        aBook.getIsbn(),
        Integer.toString(aBook.getYearOfPublication()));
  }

  private void fillInsertBookForm(
      String title, String author, Integer edition, String isbn, String year) {
    seleniumAdapter.typeIntoField("title", title);
    seleniumAdapter.typeIntoField("edition", edition.toString());
    seleniumAdapter.typeIntoField("isbn", isbn);
    seleniumAdapter.typeIntoField("author", author);
    seleniumAdapter.typeIntoField("yearOfPublication", year);
  }
}
