package co.com.avc.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SqsDtoTest {

    /**
     * Método que prueba el modelo SqsDto
     */
    @Test
    void testSqsDto() {
        // Instancia de SqsDto
        SqsDto sqsDto = new SqsDto();

        // Valor de prueba
        String message = "This is a test message";

        // Asignación de valor
        sqsDto.setMessage(message);

        // Validación de que el valor obtenido sea igual al esperado
        assertEquals(message, sqsDto.getMessage());

        // Validación de que el valor no sea nulo
        assertNotNull(sqsDto.getMessage());
    }

}