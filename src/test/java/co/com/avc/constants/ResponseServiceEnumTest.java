package co.com.avc.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseServiceEnumTest {

    @Test
    void testResponseServiceEnumValues() {
        assertEquals(500, ResponseServiceEnum.ERROR_TEC_EXCEPTION.getStatusCode());
        assertEquals("500", ResponseServiceEnum.ERROR_TEC_EXCEPTION.getServerStatusCode());
        assertEquals(SeverityEnum.ERROR.getValue(), ResponseServiceEnum.ERROR_TEC_EXCEPTION.getSeverity());
        assertEquals("Error al intentar operación sobre el directorio federado.", ResponseServiceEnum.ERROR_TEC_EXCEPTION.getStatusDesc());
        assertEquals(500, ResponseServiceEnum.ERROR_TEC_EXCEPTION.getAdditionalStatusCode());
        assertEquals("CONEXIÓN A CÁMARAS", ResponseServiceEnum.ERROR_TEC_EXCEPTION.getAdditionalStatusDesc());

        assertEquals(500, ResponseServiceEnum.ERROR_TEC_EXCEPTION_DYNAMO.getStatusCode());
        assertEquals("500", ResponseServiceEnum.ERROR_TEC_EXCEPTION_DYNAMO.getServerStatusCode());
        assertEquals(SeverityEnum.ERROR.getValue(), ResponseServiceEnum.ERROR_TEC_EXCEPTION_DYNAMO.getSeverity());
        assertEquals("Error al intentar operación sobre DynamoDB.", ResponseServiceEnum.ERROR_TEC_EXCEPTION_DYNAMO.getStatusDesc());
        assertEquals(500, ResponseServiceEnum.ERROR_TEC_EXCEPTION_DYNAMO.getAdditionalStatusCode());
        assertEquals(ComponentEnum.CREATION.getValue(), ResponseServiceEnum.ERROR_TEC_EXCEPTION_DYNAMO.getAdditionalStatusDesc());

        assertEquals(500, ResponseServiceEnum.ERROR_TEC_EXCEPTION_BANK_ID.getStatusCode());
        assertEquals("500", ResponseServiceEnum.ERROR_TEC_EXCEPTION_BANK_ID.getServerStatusCode());
        assertEquals(SeverityEnum.ERROR.getValue(), ResponseServiceEnum.ERROR_TEC_EXCEPTION_BANK_ID.getSeverity());
        assertEquals("ID del banco no identificado", ResponseServiceEnum.ERROR_TEC_EXCEPTION_BANK_ID.getStatusDesc());
        assertEquals(500, ResponseServiceEnum.ERROR_TEC_EXCEPTION_BANK_ID.getAdditionalStatusCode());
        assertEquals("CONEXIÓN A CÁMARAS", ResponseServiceEnum.ERROR_TEC_EXCEPTION_BANK_ID.getAdditionalStatusDesc());

        assertEquals(206, ResponseServiceEnum.ERROR_VALIDATE_BANNED_KEYS.getStatusCode());
        assertEquals("14", ResponseServiceEnum.ERROR_VALIDATE_BANNED_KEYS.getServerStatusCode());
        assertEquals(SeverityEnum.ERROR.getValue(), ResponseServiceEnum.ERROR_VALIDATE_BANNED_KEYS.getSeverity());
        assertEquals("La llave indicada pertenece al listado de llaves no permitidas", ResponseServiceEnum.ERROR_VALIDATE_BANNED_KEYS.getStatusDesc());
        assertEquals(14, ResponseServiceEnum.ERROR_VALIDATE_BANNED_KEYS.getAdditionalStatusCode());
        assertEquals("La llave indicada pertenece al listado de llaves no permitidas", ResponseServiceEnum.ERROR_VALIDATE_BANNED_KEYS.getAdditionalStatusDesc());
    }
}