package de.codecentric.psd.worblehat.web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;

public class NavigationControllerTest {

  @Test
  public void shouldNavigateToHome() throws Exception {
    String navigateTo = new NavigationController().home();

    assertThat(navigateTo, is("home"));
  }
}
