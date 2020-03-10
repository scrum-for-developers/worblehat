package de.codecentric.psd;

import org.springframework.boot.SpringApplication;
import org.testcontainers.containers.MySQLContainer;

public class WorblehatDev {

    public static final String MYSQL_STARTED_PROP = "mysqlStarted";

    public static void main(String[] args) {

        String mySqlStarted = System.getProperty(MYSQL_STARTED_PROP);
        if (mySqlStarted == null) {
            initMySQLContainer();
            System.setProperty(MYSQL_STARTED_PROP, "true");
        }

        SpringApplication.run(Worblehat.class, args);
    }

    private static void initMySQLContainer() {
        MySQLContainer mySQLContainer = new MySQLContainer<>()
                .withUsername("foo")
                .withPassword("bar");

                mySQLContainer.start();

        System.setProperty("spring.datasource.url", mySQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", mySQLContainer.getUsername());
        System.setProperty("spring.datasource.password", mySQLContainer.getPassword());
        System.setProperty("spring.datasource.driver-class-name", "");
    }

}
