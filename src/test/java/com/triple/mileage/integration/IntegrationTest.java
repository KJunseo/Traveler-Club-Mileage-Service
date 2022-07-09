package com.triple.mileage.integration;

import com.triple.mileage.util.DatabaseCleaner;
import com.triple.mileage.util.TestContainerConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import org.junit.jupiter.api.BeforeEach;

@Import(TestContainerConfig.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class IntegrationTest {

    @Autowired
    private DatabaseCleaner cleaner;

    @BeforeEach
    void init() {
        cleaner.execute();
    }
}
