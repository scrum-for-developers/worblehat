package de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper;

public enum Page {
  BOOK_LIST("bookList"),
  INSERT_BOOK("insertBooks"),
  BORROW_BOOK("borrow"),
  RETURN_ALL_BOOKS("returnAllBooks"),
  RETURN_SINGLE_BOOK("returnSingleBook?isbn=%s");

  private String url;

  Page(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public String getUrl(String parameter) {
    return String.format(url, parameter);
  }
}
