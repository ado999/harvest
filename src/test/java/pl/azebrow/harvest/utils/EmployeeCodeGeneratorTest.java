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

    EmployeeCodeGenerator codeGenerator;

    List<String> names = List.of("Abcd", "abcd", "ABCD", "Abc", "Ab", "A", "a", "");

    @BeforeEach
    void setup(@Mock EmployeeRepository employeeRepository) {
        codeGenerator = new EmployeeCodeGenerator(employeeRepository);
        when(employeeRepository.existsByCode(Mockito.anyString())).thenReturn(false);
    }

    @Test
    void codeMatchesPattern() {
        var results = names.stream()
                .map(codeGenerator::generateCode)
                .toList();
        for (String s : results) {
            assertThat(s, matchesPattern("[A-Z]{3}\\d{5}"));
        }
    }

    @Test
    void generatesValidPrefix(){
        List<String> validPrefixes = List.of("ABC", "ABC", "ABC", "ABC", "ABX", "AXX", "AXX", "XXX");
        var prefixes = names.stream()
                .map(codeGenerator::generateCode)
                .map(s -> s.substring(0, 3))
                .toList();
        assertLinesMatch(validPrefixes, prefixes);
    }
}