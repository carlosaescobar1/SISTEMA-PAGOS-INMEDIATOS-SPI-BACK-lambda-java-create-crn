package co.com.avc.constants;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParameterStoreEnumTest {

    @ParameterizedTest
    @EnumSource(ParameterStoreEnum.class)
    /**
     * Método que prueba los enum definidos en la clase ParameterStoreEnum.
     * Valida que el valor que se obtiene sea igual al esperado.
     */
    void parameterStoreEnumTest(ParameterStoreEnum parameter) {
        switch (parameter) {
            case PARAMETER_GENERAL_PATH_URL:
                assertEquals("/SPI/AWUE1ATHSPI-LAMBDA-TRANSV/", parameter.getValue());
                break;

            case PARAM_ARN_SNS_LOGS_OPEN_SEARCH:
                assertEquals("/SPI/AWUE1ATHSPI-LAMBDA-TRANSV/jsonArnSnsLogsOpenSearch", parameter.getValue());
                break;
        }
    }
}