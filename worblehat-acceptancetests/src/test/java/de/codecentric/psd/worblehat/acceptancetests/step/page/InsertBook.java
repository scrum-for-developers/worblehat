package de.codecentric.psd.worblehat.acceptancetests.step.page;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.PageElement;
import de.codecentric.psd.worblehat.acceptancetests.step.StoryContext;

import de.codecentric.psd.worblehat.acceptancetests.adapter.SeleniumAdapter;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class InsertBook {

	private SeleniumAdapter seleniumAdapter;

	@Autowired
	public StoryContext context;

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

	@When("a librarian adds a book with title {string}, author {string}, edition {string}, year {string} and isbn {string}")
	public void whenABookWithISBNisbnIsAdded(String title,
											 String author,
											 String edition,
											 String year,
											 String isbn) {
		seleniumAdapter.gotoPage(Page.INSERTBOOKS);
		fillInsertBookForm(title, author, edition, isbn, year);
		seleniumAdapter.clickOnPageElement(PageElement.ADDBOOKBUTTON);
		context.putObject("LAST_INSERTED_BOOK", isbn);
	}

	// *****************
	// *** T H E N *****
	// *****************
	@Then("the page contains error message {string}")
	public void pageContainsErrorMessage(String message){
		List<String> errorMsgs = seleniumAdapter.findAllStringsForElement(PageElement.ERROR);
		assertThat(errorMsgs, contains(message));
	}

	// *****************
	// *** U T I L ***** 
	// *****************


	private void fillInsertBookForm(String title, String author, String edition, String isbn,
			 String year) {
		seleniumAdapter.typeIntoField("title", title);
		seleniumAdapter.typeIntoField("edition", edition);
		seleniumAdapter.typeIntoField("isbn", isbn);
		seleniumAdapter.typeIntoField("author", author);
		seleniumAdapter.typeIntoField("yearOfPublication", year);
	}


}
