package co.com.avc.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusDirectoryEnumTest {

    @Test
    void testStatusDirectoryEnumValues() {
        // Verificar los valores de todas las constantes del enum
        assertEquals("DIRAVAL", StatusDirectoryEnum.STATUS_AVAL_DIR.getValue());
        assertEquals("DIFE", StatusDirectoryEnum.STATUS_FEDERATE_DIR.getValue());
        assertEquals("DICE", StatusDirectoryEnum.STATUS_CENTRALIZED_DIR.getValue());
    }
}