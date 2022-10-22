package pl.azebrow.harvest.integration.recovery;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

@TestConfiguration
public class ClockConfig {
    @Bean
    @Primary
    public Clock getTestClock() {
        return Clock.fixed(Instant.parse("2022-10-22T22:15:00Z"), ZoneOffset.UTC);
    }
}
