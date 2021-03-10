package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/** Controller class for the book table result. */
@Controller
@RequestMapping("/bookList")
public class BookListController {

  private BookService bookService;

  @Autowired
  public BookListController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public String setupForm(ModelMap modelMap) {
    List<Book> books = bookService.findAllBooks();
    modelMap.addAttribute("books", books);
    return "bookList";
  }
}
