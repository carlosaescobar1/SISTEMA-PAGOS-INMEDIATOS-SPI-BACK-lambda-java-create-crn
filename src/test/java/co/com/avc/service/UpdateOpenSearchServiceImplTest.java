package co.com.avc.service;

import co.com.ath.constants.ConstantsEnum;
import co.com.ath.mapper.IndexBatchMapper;
import co.com.ath.mapper.IndexRejectedMapper;
import co.com.ath.models.MessageDto;
import co.com.ath.models.MessageDtoBatch;
import co.com.ath.models.MessageDtoDynamo;
import co.com.ath.models.dynamo.AcctInfoDto;
import co.com.ath.models.dynamo.DynamoSpiDto;
import co.com.ath.models.dynamo.KeyDto;
import co.com.ath.opensearch.logs.entity.index_batch.OSIndexBatch;
import co.com.ath.redebanconn.model.HeadersRq;
import io.micronaut.context.ApplicationContext;
import io.micronaut.serde.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.UpdateResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateOpenSearchServiceImplTest {

    @Mock
    private OpenSearchClient openSearchClient;

    @Mock
    private IndexBatchMapper indexBatchMapper;

    @Mock
    private IndexRejectedMapper indexRejectedMapper;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ApplicationContext applicationContext;

    private UpdateOpenSearchServiceImpl updateOpenSearchService;
    private DynamoSpiDto dynamoSpiDto;
    private MessageDto messageDto;
    private HeadersRq headersRq;
    private OSIndexBatch osIndexBatch;

    @BeforeEach
    void setUp() {
        // Configurar paramterRetry
        int paramterRetry = 3;

        // Crear servicio
        updateOpenSearchService = new UpdateOpenSearchServiceImpl(
                openSearchClient,
                indexBatchMapper,
                indexRejectedMapper,
                paramterRetry);

        // Configurar DynamoSpiDto para pruebas
        dynamoSpiDto = new DynamoSpiDto();
        KeyDto keyDto = new KeyDto();
        keyDto.setKeyId("123456789");
        keyDto.setKeyType("CEL");
        dynamoSpiDto.setKey(keyDto);

        AcctInfoDto acctInfoDto = new AcctInfoDto();
        acctInfoDto.setBankId("001");
        dynamoSpiDto.setAcctInfo(acctInfoDto);

        // Configurar MessageDto para pruebas
        messageDto = new MessageDto();

        // Configurar MessageDtoBatch para algunas pruebas
        MessageDtoBatch messageDtoBatch = new MessageDtoBatch();
        messageDtoBatch.setIdOpensearch("test-id");

        osIndexBatch = new OSIndexBatch();
        osIndexBatch.setRetryBatch(1);
        osIndexBatch.setRqId("test-req-id");
        osIndexBatch.setRqServiceObject("{\"key\":{\"keyId\":\"123456789\",\"keyType\":\"CEL\"},\"acctInfo\":{\"bankId\":\"001\"}}");
        osIndexBatch.setFileName("test-file.csv");
        messageDtoBatch.setOsIndexBatch(osIndexBatch);

        // Configurar MessageDtoDynamo para algunas pruebas
        MessageDtoDynamo messageDtoDynamo = new MessageDtoDynamo();
        messageDtoDynamo.setDynamoSpiDto(dynamoSpiDto);
        messageDtoDynamo.setFileName("test-file.csv");

        // Configurar HeadersRq
        headersRq = new HeadersRq();
        headersRq.setRequestId("TEST-REQ-001");
    }

    @Test
    void updateElement_ShouldUpdateDocumentInOpenSearch() {
        // Arrange
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("status", "ACTIVE");

        String id = "doc-id-1";

        UpdateResponse mockResponse = mock(UpdateResponse.class);
        try {
            when(openSearchClient.update(any(Function.class), eq(Map.class))).thenReturn(mockResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Act
        updateOpenSearchService.updateElement(jsonMap, id);

        // Assert
        try {
            verify(openSearchClient).update(any(Function.class), eq(Map.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateRetryMapper_WhenRetryLessThanMax_ShouldNotBlockBatch() {
        // Arrange
        OSIndexBatch osIndexBatch = new OSIndexBatch();
        osIndexBatch.setRetryBatch(1); // 1 reintento previo, menor que paramterRetry (3)
        String fileName = "test-file.csv";
        String errorType = "ERROR-001";
        String errorDesc = "Test error";
        String rqId = "TEST-REQ-001";

        // Act
        Map<String, Object> result = updateOpenSearchService.updateRetryMapper(
                osIndexBatch, fileName, errorType, errorDesc, rqId);

        // Assert
        assertEquals(2, result.get("retryBatch")); // Debería incrementar en 1
        assertEquals(ConstantsEnum.UNLOCK_BATCH.getValue(), result.get("blockedBatch")); // Debería estar desbloqueado
        // No debería guardarse en rejected index porque no ha alcanzado el máximo
    }

    @Test
    void processSuccessBatchAction_WithoutMessageDtoBatch_ShouldDoNothing() {
        // Arrange
        messageDto.setMessageDtoBatch(null);

        // Act
        updateOpenSearchService.processSuccessBatchAction(messageDto);

        // Assert - Verifica que no se llame a ningún método de actualización
        try {
            verify(openSearchClient, times(0)).update(any(Function.class), any());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void processOpensearchAction_WithMessageDtoBatch_ShouldUpdateRetry() {
        // Arrange
        String fileName = "test-file.csv";
        String errorType = "ERROR-001";
        String errorDesc = "Test error";
        String rqId = "TEST-REQ-001";
        String subject = "test-subject";

        MessageDtoBatch messageDtoBatch = new MessageDtoBatch();
        messageDtoBatch.setIdOpensearch("test-id");
        OSIndexBatch osIndexBatch = new OSIndexBatch();
        osIndexBatch.setRetryBatch(1);
        messageDtoBatch.setOsIndexBatch(osIndexBatch);

        messageDto.setMessageDtoBatch(messageDtoBatch);

        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("retryBatch", 2);
        updateMap.put("blockedBatch", ConstantsEnum.UNLOCK_BATCH.getValue());

        OSIndexBatch mockBatch = new OSIndexBatch();

        when(indexBatchMapper.mapEnrollmentRqToIndexBatch(
                eq(dynamoSpiDto), eq(headersRq), anyString(), eq(fileName)))
                .thenReturn(mockBatch);

        // Act
        updateOpenSearchService.processOpensearchAction(
                messageDto, dynamoSpiDto, headersRq, fileName, errorType, errorDesc, rqId, subject);

        // Assert
        try {
            verify(openSearchClient).update(any(Function.class), eq(Map.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}