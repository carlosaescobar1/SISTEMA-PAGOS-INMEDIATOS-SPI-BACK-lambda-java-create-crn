package co.com.avc.constants;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseStatusCodeEnumTest {

    @ParameterizedTest
    @EnumSource(ResponseStatusCodeEnum.class)
    /**
     * Método que prueba los enum definidos en la clase ResponseCodeEnum.
     * Valida que el valor que se obtiene sea igual al esperado.
     */
    void responseCodeEnumTest(ResponseStatusCodeEnum responseCode) {
        switch (responseCode) {
            case ENTERPRISE_SUCCESS_STATUS_CODE:
                assertEquals(200, responseCode.getValue());
                break;
        }
    }
}