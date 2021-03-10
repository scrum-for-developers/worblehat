package de.codecentric.psd.worblehat.acceptancetests.step.page;

import static de.codecentric.psd.worblehat.acceptancetests.step.StepUtilities.doWithEach;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import de.codecentric.psd.worblehat.acceptancetests.adapter.SeleniumAdapter;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.PageElement;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class BorrowBook {

  private SeleniumAdapter seleniumAdapter;

  @Autowired
  public BorrowBook(SeleniumAdapter seleniumAdapter) {
    this.seleniumAdapter = seleniumAdapter;
  }

  // *******************
  // *** G I V E N *****
  // *******************

  // *****************
  // *** W H E N *****s
  // *****************

  @When("{string} borrows the book(s) {string}")
  public void whenUseruserBorrowsTheBookisbn(String user, String isbns) {
    doWithEach(
        isbns,
        (isbn) -> {
          seleniumAdapter.gotoPage(Page.BORROWBOOK);
          seleniumAdapter.typeIntoField("email", user);
          seleniumAdapter.typeIntoField("isbn", isbn);
          seleniumAdapter.clickOnPageElement(PageElement.BORROWBOOKBUTTON);
        });
  }

  // *****************
  // *** T H E N *****
  // *****************

  @Then(
      "{string} gets the error {string}, when trying to borrow the book with one of the {string} again")
  public void whenBorrowerBorrowsBorrowedBookShowErrorMessage(
      String borrower, String message, String isbns) {
    doWithEach(
        isbns,
        (isbn) -> {
          seleniumAdapter.gotoPage(Page.BORROWBOOK);
          seleniumAdapter.typeIntoField("email", borrower);
          seleniumAdapter.typeIntoField("isbn", isbn);
          seleniumAdapter.clickOnPageElement(PageElement.BORROWBOOKBUTTON);
          String errorMessage = seleniumAdapter.getTextFromElement(PageElement.ISBN_ERROR);
          assertThat(errorMessage, is(message));
        });
  }
}
