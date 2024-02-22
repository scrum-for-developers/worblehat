package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.BookDataFormData;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/** Handles requests for the application home page. */
@Controller
@RequestMapping("/insertBooks")
public class InsertBookController {

  private static final Logger LOG = LoggerFactory.getLogger(InsertBookController.class);

  private BookService bookService;

  @Autowired
  public InsertBookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public void setupForm(ModelMap modelMap) {
    modelMap.put("bookDataFormData", new BookDataFormData());
  }

  @PostMapping
  public String processSubmit(
      @ModelAttribute("bookDataFormData") @Valid BookDataFormData bookDataFormData,
      BindingResult result) {

    if (result.hasErrors()) {
      return "insertBooks";
    } else {
      Optional<Book> book =
          bookService.createBook(
              bookDataFormData.getTitle(),
              bookDataFormData.getAuthor(),
              bookDataFormData.getEdition(),
              bookDataFormData.getIsbn(),
              Integer.parseInt(bookDataFormData.getYearOfPublication()));
      if (book.isPresent()) {
        Book newBook = book.get();
        // === HINT ===
        // you can modify the book or set new properties here
        bookService.updateBook(newBook);
        LOG.info("new book instance is created: {}", newBook);
      } else {
        LOG.debug("failed to create new book with: {}", bookDataFormData);
        result.reject("duplicateIsbn");
        return "insertBooks";
      }
      return "redirect:bookList";
    }
  }
}
