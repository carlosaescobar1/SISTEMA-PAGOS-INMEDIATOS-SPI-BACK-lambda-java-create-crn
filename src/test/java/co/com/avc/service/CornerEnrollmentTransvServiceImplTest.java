package co.com.avc.service;

import co.com.ath.commons.util.ATHException;
import co.com.ath.commons.util.Util;
import co.com.avc.constants.ConsentEnum;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.constants.ResponseServiceEnum;
import co.com.avc.constants.ResponseStatusCodeEnum;
import co.com.avc.cornerconn.models.HttpResponseWrapper;
import co.com.avc.cornerconn.service.enrollment.ICornerEnrollmentAccountService;
import co.com.avc.entity.DynamoSpiEntity;
import co.com.avc.mapper.IDynamoMapper;
import co.com.avc.mapper.IndexBatchMapper;
import co.com.avc.mapper.RequestMapper;
import co.com.avc.models.MessageDto;
import co.com.avc.models.dynamo.DynamoSpiDto;
import co.com.avc.models.parameter.ParamFlowConfig;
import co.com.avc.models.parameter.ParamVaultUpload;
import co.com.avc.models.parameter.VaultServicesTimeOut;
import co.com.avc.repository.DynamoRepository;
import co.com.avc.util.TimeLineUtil;
import co.com.avc.util.VaultSelectorUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CornerEnrollmentTransvServiceImplTest {

    @InjectMocks
    private CornerEnrollmentTransvServiceImpl cornerEnrollmentService;

    @Mock
    private RequestMapper requestMapper;

    @Mock
    private IUpdateOpenSearchService updateOpenSearchService;

    @Mock
    private IOpenSearchSynchService openSearchSynchService;

    @Mock
    private TimeLineUtil timeLineUtil;

    @Mock
    private IndexBatchMapper indexBatchMapper;

    @Mock
    private ParamFlowConfig paramFlowConfig;

    @Mock
    private VaultSelectorUtil vaultSelectorUtil;

    @Mock
    private VaultServicesTimeOut vaultServicesTimeOut;

    @Mock
    private DynamoRepository dynamoRepository;

    @Mock
    private IDynamoMapper dynamoMapper;

    @Mock
    private ICornerEnrollmentAccountService cornerEnrollmentAccountService;

    @Mock
    private MessageDto messageDto;

    @Mock
    private DynamoSpiDto dynamoSpiDto;

    @Mock
    private HttpResponseWrapper httpResponseWrapper;

    @Test
    void cornerEnrollServiceProcessesActiveFlowSuccessfully() throws IOException {
        when(paramFlowConfig.getVaultSyncFlow()).thenReturn(ConstantsEnum.ACTIVE_FLOW.getValue());
        when(dynamoSpiDto.getConsent()).thenReturn(ConsentEnum.S.getValue());
        when(dynamoSpiDto.getEffDtConsent()).thenReturn("2023-01-01");
        when(vaultSelectorUtil.selectorVault()).thenReturn(mock(ParamVaultUpload.class));
        when(cornerEnrollmentAccountService.enrollmentKeyService(any(), anyString(), anyInt()))
                .thenReturn(httpResponseWrapper);
        when(httpResponseWrapper.getStatusCode()).thenReturn(ResponseStatusCodeEnum.PERSON_SUCCESS_STATUS_CODE.getValue());
        when(httpResponseWrapper.getResponseBody()).thenReturn("{\"message\":\"Success\"}");

        String response = Util.object2String(httpResponseWrapper);

        System.out.println(response);

        cornerEnrollmentService.cornerEnrollService(messageDto, "subject", dynamoSpiDto, "rqId", "dateOperation", "rqUUID");

        verify(updateOpenSearchService).processSuccessBatchAction(messageDto);
    }

    @Test
    void cornerEnrollServiceHandlesInactiveFlow() {
        when(paramFlowConfig.getVaultSyncFlow()).thenReturn("INACTIVE");

        cornerEnrollmentService.cornerEnrollService(messageDto, "subject", dynamoSpiDto, "rqId", "dateOperation", "rqUUID");

        verify(dynamoRepository).save(any(DynamoSpiEntity.class));
        verify(openSearchSynchService).openSearchSyncEnroll(any(DynamoSpiEntity.class));
    }

    @Test
    void cornerEnrollServiceThrowsATHExceptionOnIOException() throws IOException {
        when(paramFlowConfig.getVaultSyncFlow()).thenReturn(ConstantsEnum.ACTIVE_FLOW.getValue());
        when(dynamoSpiDto.getConsent()).thenReturn(ConsentEnum.S.getValue());
        when(dynamoSpiDto.getEffDtConsent()).thenReturn("2023-01-01");
        when(vaultSelectorUtil.selectorVault()).thenReturn(mock(ParamVaultUpload.class));
        when(cornerEnrollmentAccountService.enrollmentKeyService(any(), anyString(), anyInt()))
                .thenThrow(new IOException("Connection error"));

        ATHException exception = assertThrows(ATHException.class, () ->
                cornerEnrollmentService.cornerEnrollService(messageDto, "subject", dynamoSpiDto, "rqId", "dateOperation", "rqUUID")
        );

        assertEquals(ResponseServiceEnum.ERROR_TEC_EXCEPTION.getServerStatusCode(), exception.getErrorCode());
    }

    @Test
    void cornerEnrollServiceProcessesConsentFlowSuccessfully() {
        when(paramFlowConfig.getVaultSyncFlow()).thenReturn(ConstantsEnum.ACTIVE_FLOW.getValue());
        when(paramFlowConfig.getConsentFlow()).thenReturn(ConstantsEnum.ACTIVE_FLOW.getValue());
        when(dynamoSpiDto.getConsent()).thenReturn(ConstantsEnum.S.getValue());
        when(dynamoSpiDto.getEffDtConsent()).thenReturn("2023-01-01");

        cornerEnrollmentService.cornerEnrollService(messageDto, "subject", dynamoSpiDto, "rqId", "dateOperation", "rqUUID");

        verify(updateOpenSearchService).processSuccessBatchAction(messageDto);
    }

    @Test
    void cornerEnrollServiceHandlesNullConsentDateGracefully() {
        when(paramFlowConfig.getVaultSyncFlow()).thenReturn(ConstantsEnum.ACTIVE_FLOW.getValue());
        when(dynamoSpiDto.getConsent()).thenReturn(ConstantsEnum.S.getValue());
        when(dynamoSpiDto.getEffDtConsent()).thenReturn(null);

        cornerEnrollmentService.cornerEnrollService(messageDto, "subject", dynamoSpiDto, "rqId", "dateOperation", "rqUUID");

        verifyNoInteractions(cornerEnrollmentAccountService);
    }
}