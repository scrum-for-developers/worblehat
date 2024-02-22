package de.codecentric.psd.worblehat.web.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NavigationControllerTest {

  @Test
  void shouldNavigateToHome() throws Exception {
    String navigateTo = new NavigationController().home();

    assertThat(navigateTo).isEqualTo("home");
  }
}
