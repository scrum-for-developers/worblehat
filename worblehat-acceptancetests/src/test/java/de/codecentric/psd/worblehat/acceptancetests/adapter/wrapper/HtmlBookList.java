package de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HtmlBookList {
  private List<WebElement> headers;
  private Map<String, HtmlBook> values;

  public HtmlBookList(WebElement table) {

    headers = table.findElements(By.cssSelector("thead tr th"));

    WebElement tbody = table.findElement(By.tagName("tbody"));
    extractValues(tbody);
  }

  private void extractValues(WebElement tbody) {
    values = new HashMap<>();
    for (WebElement row : tbody.findElements(By.tagName("tr"))) {
      List<WebElement> cells = row.findElements(By.cssSelector("td,th"));

      HtmlBook book = new HtmlBook();
      int currentColumn = 0;
      for (WebElement column : headers) {
        switch (column.getText()) {
          // .getAttribute("textContent") preserves eventually existing whitespace while .getText() does not
          case "Title":
            book.setTitle(cells.get(currentColumn).getAttribute("textContent"));
            break;
          case "Author":
            book.setAuthor(cells.get(currentColumn).getAttribute("textContent"));
            break;
          case "Year":
            book.setYearOfPublication(cells.get(currentColumn).getAttribute("textContent"));
            break;
          case "Edition":
            book.setEdition(Integer.parseInt(cells.get(currentColumn).getAttribute("textContent")));
            break;
          case "Borrower":
            book.setBorrower(cells.get(currentColumn).getAttribute("textContent"));
            break;
          case "ISBN":
            book.setIsbn(cells.get(currentColumn).getAttribute("textContent"));
            break;
          case "Description":
            book.setDescription(cells.get(currentColumn).getAttribute("textContent"));
        }
        currentColumn++;
      }

      values.put(book.getIsbn(), book);
    }
  }

  public int size() {
    return values.size();
  }

  public HtmlBook getBookByIsbn(String isbn) {
    return values.get(isbn);
  }

  public Map<String, HtmlBook> getHtmlBooks() {
    return values;
  }
}
