package co.com.avc.constants;

import org.junit.jupiter.api.Test;

class PersonTypeEnumTest {

    @Test
    void testPersonTypeEnumValues() {
        // Verificar los valores de todas las constantes del enum
        assertEquals("PN", PersonTypeEnum.PN.getValue());
        assertEquals("PJ", PersonTypeEnum.PJ.getValue());
    }
}