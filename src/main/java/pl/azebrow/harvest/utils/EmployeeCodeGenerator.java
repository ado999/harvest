package pl.azebrow.harvest.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.azebrow.harvest.repository.EmployeeRepository;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class EmployeeCodeGenerator {

    private final static int PREFIX_LENGTH = 3;
    private final static int POSTFIX_LENGTH = 5;

    private final EmployeeRepository employeeRepository;

    public String generateCode(String name) {
        String prefix = generatePrefix(name);
        String postfix = generatePostfix();
        String code = prefix.concat(postfix);
        //todo this is risky
        if (employeeRepository.existsByCode(code)) {
            return generateCode(name);
        }
        return code;
    }

    private String generatePrefix(String name) {
        int len = name.length();
        String nameSubstring = len <= PREFIX_LENGTH ? name : name.substring(0, PREFIX_LENGTH);
        String prefix = String
                .format("%-" + PREFIX_LENGTH + "s", nameSubstring)
                .replace(' ', 'X');
        return prefix.toUpperCase();
    }

    private String generatePostfix() {
        Random random = new Random();
        int number = random.nextInt();
        number = Math.abs(number);
        if (number < 10000) {
            number += 10000;
        }
        return String.valueOf(number).substring(0, POSTFIX_LENGTH);
    }

}
