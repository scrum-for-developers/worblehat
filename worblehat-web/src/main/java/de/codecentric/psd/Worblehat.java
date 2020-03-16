package de.codecentric.psd;

import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class Worblehat {

  @Configuration
  public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
    }
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    ConfigurableApplicationContext applicationContext =
        SpringApplication.run(Worblehat.class, args);

    // this code is basically to (a) demonstrate how to stop a Spring application
    // and (b)
    // get rid of the SonarQube warning to close the context properly
    System.out.println("Enter 'stop' to stop Worblehat.");
    String line = "";
    do {
      line = scan.nextLine();
    } while (!line.equals("stop"));
    applicationContext.close();
  }
}
