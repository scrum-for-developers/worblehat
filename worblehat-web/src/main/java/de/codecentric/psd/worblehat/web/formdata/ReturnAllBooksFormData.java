package de.codecentric.psd.worblehat.web.formdata;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/** This class represent the form data of the return book form. */
public class ReturnAllBooksFormData {

  @NotEmpty(message = "{empty.returnAllBookFormData.emailAddress}")
  @Email(message = "{notvalid.returnAllBookFormData.emailAddress}")
  private String emailAddress;

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }
}
