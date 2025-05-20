package co.com.avc.constants;

import org.junit.jupiter.api.Test;

class GestionEnumTest {

    @Test
    void testGestionEnumValues() {
        // Verificar los valores de todas las constantes del enum
        assertEquals("username", GestionEnum.VAR_USER.getValue());
        assertEquals("password", GestionEnum.VAR_KEY.getValue());
        assertEquals("host", GestionEnum.VAR_HOST.getValue());
        assertEquals("port", GestionEnum.VAR_PORT.getValue());
        assertEquals("schema", GestionEnum.VAR_SCHEMA.getValue());
    }
}