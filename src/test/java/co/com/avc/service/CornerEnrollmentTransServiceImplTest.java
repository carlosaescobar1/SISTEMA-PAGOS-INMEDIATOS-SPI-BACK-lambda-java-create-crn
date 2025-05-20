package co.com.avc.service;

import co.com.ath.commons.util.ATHException;
import co.com.avc.constants.ConsentEnum;
import co.com.avc.constants.ConstantsEnum;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CornerEnrollmentTransvServiceImplTest {

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

    private CornerEnrollmentTransvServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new CornerEnrollmentTransvServiceImpl(
                requestMapper,
                updateOpenSearchService,
                openSearchSynchService,
                timeLineUtil,
                indexBatchMapper,
                paramFlowConfig,
                vaultSelectorUtil,
                vaultServicesTimeOut,
                dynamoRepository
        );
    }

    @Test
    @DisplayName("cornerEnrollService should process consent N and save to Dynamo and OpenSearch")
    void cornerEnrollServiceProcessesConsentN() {
        MessageDto messageDto = mock(MessageDto.class);
        DynamoSpiDto dynamoSpiDto = mock(DynamoSpiDto.class);
        when(dynamoSpiDto.getConsent()).thenReturn(ConsentEnum.N.getValue());
        when(paramFlowConfig.getVaultSyncFlow()).thenReturn(ConstantsEnum.ACTIVE_FLOW.getValue());

        service.cornerEnrollService(messageDto, "subject", dynamoSpiDto, "rqId", "dateOperation", "rqUUID");

        verify(dynamoRepository).save(any());
        verify(openSearchSynchService).openSearchSyncEnroll(any());
        verify(updateOpenSearchService).processSuccessBatchAction(messageDto);
    }

    @Test
    @DisplayName("cornerEnrollService should throw ATHException on IOException")
    void cornerEnrollServiceThrowsATHExceptionOnIOException() {
        MessageDto messageDto = mock(MessageDto.class);
        DynamoSpiDto dynamoSpiDto = mock(DynamoSpiDto.class);
        when(paramFlowConfig.getVaultSyncFlow()).thenReturn(ConstantsEnum.ACTIVE_FLOW.getValue());
        doThrow(new ATHException("500", "Error", 500)).when(timeLineUtil).sendLogRq(any(), any());

        assertThrows(ATHException.class, () -> service.cornerEnrollService(messageDto, "subject", dynamoSpiDto, "rqId", "dateOperation", "rqUUID"));
    }

    @Test
    @DisplayName("cornerEnrollService should process consent S and call enrollmentKeyService")
    void cornerEnrollServiceProcessesConsentS() {
        MessageDto messageDto = mock(MessageDto.class);
        DynamoSpiDto dynamoSpiDto = mock(DynamoSpiDto.class);
        ParamVaultUpload paramVaultUpload = mock(ParamVaultUpload.class);
        when(dynamoSpiDto.getConsent()).thenReturn(ConsentEnum.S.getValue());
        when(dynamoSpiDto.getEffDtConsent()).thenReturn("2023-01-01");
        when(paramFlowConfig.getVaultSyncFlow()).thenReturn(ConstantsEnum.ACTIVE_FLOW.getValue());
        when(vaultSelectorUtil.selectorVault()).thenReturn(paramVaultUpload);
        when(paramVaultUpload.getUrlEnrollmentVault()).thenReturn("http://example.com");

        service.cornerEnrollService(messageDto, "subject", dynamoSpiDto, "rqId", "dateOperation", "rqUUID");

        verify(updateOpenSearchService).processSuccessBatchAction(messageDto);
    }

    @Test
    @DisplayName("cornerEnrollService should skip processing when effDtConsent is null")
    void cornerEnrollServiceSkipsWhenEffDtConsentIsNull() {
        MessageDto messageDto = mock(MessageDto.class);
        DynamoSpiDto dynamoSpiDto = mock(DynamoSpiDto.class);
        when(dynamoSpiDto.getConsent()).thenReturn(ConsentEnum.S.getValue());
        when(dynamoSpiDto.getEffDtConsent()).thenReturn(null);
        when(paramFlowConfig.getVaultSyncFlow()).thenReturn(ConstantsEnum.ACTIVE_FLOW.getValue());

        service.cornerEnrollService(messageDto, "subject", dynamoSpiDto, "rqId", "dateOperation", "rqUUID");

        verifyNoInteractions(updateOpenSearchService);
    }

    @Test
    @DisplayName("cornerEnrollService should process non-active flow and save to Dynamo")
    void cornerEnrollServiceProcessesNonActiveFlow() {
        MessageDto messageDto = mock(MessageDto.class);
        DynamoSpiDto dynamoSpiDto = mock(DynamoSpiDto.class);
        when(paramFlowConfig.getVaultSyncFlow()).thenReturn(ConstantsEnum.INACTIVE_FLOW.getValue());

        service.cornerEnrollService(messageDto, "subject", dynamoSpiDto, "rqId", "dateOperation", "rqUUID");

        verify(dynamoRepository).save(any());
        verify(openSearchSynchService).openSearchSyncEnroll(any());
    }
}