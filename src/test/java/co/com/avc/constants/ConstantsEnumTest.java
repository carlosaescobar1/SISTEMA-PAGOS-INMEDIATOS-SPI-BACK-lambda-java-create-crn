package co.com.avc.constants;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConstantsEnumTest {

    @ParameterizedTest
    @EnumSource(ConstantsEnum.class)
    /**
     * Método que prueba los enum definidos en la clase ConstantsEnum.
     * Valida que el valor que se obtiene sea igual al esperado.
     */
    void constantsEnumTest(ConstantsEnum constant) {
        switch (constant) {
            case ERROR_SERVICE:
                assertEquals("Error durante el proceso de creación (Exception): ", constant.getValue());
                break;

            case ERROR_ATH_SERVICE:
                assertEquals("Error durante el proceso de creación (ATHException): ", constant.getValue());
                break;

            case VAULT_NAME:
                assertEquals("REDEBAN", constant.getValue());
                break;

            case CEL:
                assertEquals("2", constant.getValue());
                break;

            case EVENT_BATCH_CONSENT:
                assertEquals("consent", constant.getValue());
                break;

            case EMPTY_STRING:
                assertEquals("", constant.getValue());
                break;

            case FIRST_ARRAY_ITEM:
                assertEquals("0", constant.getValue());
                break;

            case PN:
                assertEquals("PN", constant.getValue());
                break;

            case CORNER:
                assertEquals("REDEBAN", constant.getValue());
                break;

            case CORNER_PERSON:
                assertEquals("REDEBAN_PERSON", constant.getValue());
                break;

            case ERROR_SAVE:
                assertEquals("No se pudo realizar el guardado del documento ", constant.getValue());
                break;

            case ERROR_CONNECTION:
                assertEquals("No se esta generando la conexion con OpenSearch Correctamente ", constant.getValue());
                break;

            case PENDING_BATCH:
                assertEquals("Pendiente", constant.getValue());
                break;

            case FAILED_BATCH:
                assertEquals("Fallido", constant.getValue());
                break;

            case SUCCESS_BATCH:
                assertEquals("Exitoso", constant.getValue());
                break;

            case BLOCK_BATCH:
                assertEquals("1", constant.getValue());
                break;

            case UNLOCK_BATCH:
                assertEquals("0", constant.getValue());
                break;

            case S:
                assertEquals("S", constant.getValue());
                break;

            case N:
                assertEquals("N", constant.getValue());
                break;

            case SUBJECT_BATCH:
                assertEquals("batch", constant.getValue());
                break;

            case SUBJECT_MIGRATION:
                assertEquals("MIG", constant.getValue());
                break;

            case HEADER_CHANNEL:
                assertEquals("OFVV", constant.getValue());
                break;

            case ACTIVE_FLOW:
                assertEquals("ACTIVA", constant.getValue());
                break;

            case INDEX_KEY_TEMPLATE:
                assertEquals("TemplateKey", constant.getValue());
                break;

            case INDEX_BATCH_BY_KEY_TEMPLATE:
                assertEquals("TemplateBatchByKey", constant.getValue());
                break;

            case INDEX_MIGRATE:
                assertEquals("migrate", constant.getValue());
                break;

            case OP_PARAMETER_KEY_TYPE:
                assertEquals("keyType", constant.getValue());
                break;

            case OP_PARAMETER_KEY_VALUE:
                assertEquals("keyId", constant.getValue());
                break;

            case OP_PARAMETER_BLOCKED_BATCH:
                assertEquals("blockedBatch", constant.getValue());
                break;
        }
    }
}