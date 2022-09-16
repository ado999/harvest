package pl.azebrow.harvest.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QrGeneratorTest {

    QrGenerator qrGenerator;

    @BeforeEach
    void setup(){
        qrGenerator = new QrGenerator();
    }

    @Test
    void generatesData() {
        var data = qrGenerator.generate("ABC12345");
        assertNotNull(data);
        assertTrue(data.length > 0);
    }
}