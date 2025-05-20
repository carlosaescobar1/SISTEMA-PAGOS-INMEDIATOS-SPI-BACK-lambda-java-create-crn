package co.com.avc.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComponentEnumTest {

    @Test
    void testComponentEnumValues() {
        // Verificar los valores de todas las constantes del enum
        assertEquals("DYNAMO_DB", ComponentEnum.DYNAMO_DB.getValue());
        assertEquals("VALIDATIONS", ComponentEnum.VALIDATIONS.getValue());
        assertEquals("CREATION", ComponentEnum.CREATION.getValue());
    }
}