package de.codecentric.psd;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest()
@ExtendWith(SpringExtension.class)
class WorblehatSystemIT {

  @Test
  void shouldStartApplication() {
    assertDoesNotThrow(() -> {});
    // Intentionally left blank, this test should just make sure that the application can be started
  }
}
