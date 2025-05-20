package co.com.avc.constants;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class SeverityEnumTest {

    @ParameterizedTest
    @EnumSource(SeverityEnum.class)
    /**
     * Método que prueba los enum definidos en la clase SeverityEnum.
     * Valida que los valores obtenidos sean iguales a los esperados.
     */
    void severityEnumTest(SeverityEnum severity) {
        switch (severity) {
            case IFO:
                assertEquals("Info", severity.getValue());
                break;
            case WARNING:
                assertEquals("Warning", severity.getValue());
                break;
            case ERROR:
                assertEquals("Error", severity.getValue());
                break;
        }
    }
}