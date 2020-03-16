package de.codecentric.psd.worblehat.acceptancetests.step.page;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import de.codecentric.psd.worblehat.acceptancetests.adapter.SeleniumAdapter;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.PageElement;
import de.codecentric.psd.worblehat.acceptancetests.step.StoryContext;
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

  @When(
      "a librarian adds a book with title {string}, author {string}, edition {string}, year {string} and isbn {string}")
  public void whenABookWithISBNisbnIsAdded(
      String title, String author, String edition, String year, String isbn) {
    seleniumAdapter.gotoPage(Page.INSERTBOOKS);
    fillInsertBookForm(title, author, edition, isbn, year);
    seleniumAdapter.clickOnPageElement(PageElement.ADDBOOKBUTTON);
    context.putObject("LAST_INSERTED_BOOK", isbn);
  }

  // *****************
  // *** T H E N *****
  // *****************
  @Then("the page contains error message for field {string}")
  public void pageContainsErrorMessage(String field) {
    String errorMessage =
        seleniumAdapter.getTextFromElement(
            ("isbn".equals(field) ? PageElement.ISBN_ERROR : PageElement.EDITION_ERROR));
    assertThat(errorMessage, notNullValue());
  }
  // *****************
  // *** U T I L *****
  // *****************

  private void fillInsertBookForm(
      String title, String author, String edition, String isbn, String year) {
    seleniumAdapter.typeIntoField("title", title);
    seleniumAdapter.typeIntoField("edition", edition);
    seleniumAdapter.typeIntoField("isbn", isbn);
    seleniumAdapter.typeIntoField("author", author);
    seleniumAdapter.typeIntoField("yearOfPublication", year);
  }
}
