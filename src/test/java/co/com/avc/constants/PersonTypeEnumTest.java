package co.com.avc.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTypeEnumTest {

    @Test
    void testPersonTypeEnumValues() {
        // Verificar los valores de todas las constantes del enum
        assertEquals("PN", PersonTypeEnum.PN.getValue());
        assertEquals("PJ", PersonTypeEnum.PJ.getValue());
    }
}