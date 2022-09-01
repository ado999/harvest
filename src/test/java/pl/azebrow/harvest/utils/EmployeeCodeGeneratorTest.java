package pl.azebrow.harvest.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.azebrow.harvest.repository.EmployeeRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeCodeGeneratorTest {

    EmployeeRepository employeeRepository;

    List<String> names = List.of("Abcd", "abcd", "ABCD", "Abc", "Ab", "A", "a", "");
    List<String> validPrefixes = List.of("ABC", "ABC", "ABC", "ABC", "ABX", "AXX", "AXX", "XXX");

    @BeforeEach
    void init(@Mock EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        when(employeeRepository.existsByCode(Mockito.anyString())).thenReturn(false);
    }

    @Test
    void shouldGenerateValidCode() {
        var codeGenerator = new EmployeeCodeGenerator(employeeRepository);
        var results = names.stream()
                .map(codeGenerator::generateCode)
                .toList();
        var prefixes = results.stream()
                .map(s -> s.substring(0, 3))
                .toList();
        assertLinesMatch(validPrefixes, prefixes);
        for (String s : results) {
            assertThat(s, matchesPattern("[A-Z]{3}\\d{5}"));
        }

    }
}