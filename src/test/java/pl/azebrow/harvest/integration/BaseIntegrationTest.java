package pl.azebrow.harvest.integration;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
@ContextConfiguration(initializers = BaseIntegrationTest.TestEnvInitializers.class)
@AutoConfigureMockMvc
@Transactional
@DirtiesContext
public class BaseIntegrationTest {

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    protected MockMvc mockMvc;

    @Container
    private static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>("postgres:14.1").withDatabaseName("harvest").withUsername("harvest").withPassword("harvest")/*.withInitScript("init.sql")*/;

    protected JsonUtils jsonUtils = new JsonUtils();

    static class TestEnvInitializers implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            TestPropertyValues values = TestPropertyValues.of("spring.datasource.url=" + postgresDB.getJdbcUrl(), "spring.datasource.password" + postgresDB.getPassword(), "spring.datasource.username" + postgresDB.getUsername());
            values.applyTo(applicationContext);
        }
    }

}
