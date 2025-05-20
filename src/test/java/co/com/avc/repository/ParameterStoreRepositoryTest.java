package co.com.avc.repository;

import co.com.ath.commons.util.ParameterStoreUtil;
import co.com.ath.commons.util.Util;
import co.com.avc.models.parameter.ParameterStoreDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

class ParameterStoreRepositoryTest {

    ParameterStoreRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ParameterStoreRepository();
    }

    @Test
    void getParameters() {

        /*
         * Se crean mocks estáticos
         */
        try (MockedStatic<ParameterStoreUtil> mockedParameterStoreUtil = Mockito.mockStatic(ParameterStoreUtil.class);
             MockedStatic<Util> utilMock = Mockito.mockStatic(Util.class)) {

            /*
             * Se asignan valores de prueba
             */
            Map<String, String> generalParameterMock = new HashMap<>();
            generalParameterMock.put("/SPI/AWUE1ATHSPI-LAMBDA-TRANSV/arnSnsLogsOpenSearch", "arnTest");

            mockedParameterStoreUtil.when(() -> ParameterStoreUtil.getParameters(any()))
                    .thenReturn(generalParameterMock);

            /*
             * Se realiza llamado a método getParameters
             */
            ParameterStoreDto result = repository.getParameters();

            /*
             * Valida que el resultado del llamado al método getParameters no sea nulo
             */
            assertNotNull(result);
//            assertEquals("arnTest", result.getArnSnsLogsOpenSearch());

        }

    }
}