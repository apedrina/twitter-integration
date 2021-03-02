package com.alissonpedrina.consumer.it;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ExtendWith(SpringExtension.class)
@DirtiesContext
@SpringBootTest
@TestPropertySource("classpath:application-integration-containers.properties")
@Testcontainers
public abstract class AbstractIT {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:9.4");

    static {
        postgreSQLContainer.start();

    }

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }
}
