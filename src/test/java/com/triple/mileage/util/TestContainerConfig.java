package com.triple.mileage.util;

import org.springframework.boot.test.context.TestConfiguration;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@TestConfiguration
public class TestContainerConfig {

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.0.22")
            .withDatabaseName("testdb");
}
