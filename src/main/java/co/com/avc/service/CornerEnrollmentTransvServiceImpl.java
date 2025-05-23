package co.com.avc.service;

import co.com.ath.commons.util.ATHException;
import co.com.ath.commons.util.Util;
import co.com.ath.opensearch.logs.constants.ActionConstants;
import co.com.ath.opensearch.logs.constants.IndexConstants;
import co.com.ath.opensearch.logs.entity.index_batch.OSIndexBatch;
import co.com.avc.constants.ConsentEnum;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.constants.ResponseServiceEnum;
import co.com.avc.constants.ResponseStatusCodeEnum;
import co.com.avc.cornerconn.models.HttpResponseWrapper;
import co.com.avc.cornerconn.models.MsgInformationResponse;
import co.com.avc.cornerconn.models.enrollment.EnrollmentRq;
import co.com.avc.cornerconn.service.enrollment.CornerEnrollmentAccountServiceImpl;
import co.com.avc.cornerconn.service.enrollment.ICornerEnrollmentAccountService;
import co.com.avc.entity.DynamoSpiEntity;
import co.com.avc.mapper.DynamoMapperImpl;
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
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;

@Slf4j
@AllArgsConstructor
public class CornerEnrollmentTransvServiceImpl implements ICornerEnrollmentTransvService {
    /**
     * Interfaz del servicio de creación de Corner
     */
    @Inject
    private final ICornerEnrollmentAccountService cornerEnrollmentService = new CornerEnrollmentAccountServiceImpl();

    /**
     * Clase que contiene los metodos de mapeo del body de la petición
     * de Corner
     */
    private final RequestMapper requestMapper;

    /**
     * Interfaz que llama al método de actualización de opensearch
     */
    private final IUpdateOpenSearchService updateOpenSearchService;

    /**
     * Interfaz que llama al método de sincronización opensearch
     */
    private final IOpenSearchSynchService openSearchSynchService;

    /**
     * Clase que llama a los métodos para hacer el uso de
     * logs de timeLine
     */
    private final TimeLineUtil timeLineUtil;

    /**
     * Clase que contiene los métodos para mapear al objeto de
     * sonda
     */
    private final IndexBatchMapper indexBatchMapper;

    /**
     * Consentimiento del parameter store
     */
    private final ParamFlowConfig paramFlowConfig;

    /**
     * Objeto con la información de la camara de Corner.
     */
    private final VaultSelectorUtil vaultSelectorUtil;

    /**
     * Objeto que contiene los valores de los timeOut
     */
    private final VaultServicesTimeOut vaultServicesTimeOut;

    /**
     * Clase que contiene las operaciones de dynamo
     */
    private final DynamoRepository dynamoRepository;

    private final IDynamoMapper dynamoMapper = new DynamoMapperImpl();

    /**
     * Método encargado de realizar el consumo
     * a la camara de corner
     *
     * @param messageDto
     * @param subject
     */
    @Override
    public void cornerEnrollService(MessageDto messageDto, String subject, DynamoSpiDto dynamoSpiDto, String rqId, String dateOperation, String rqUUID) {

        log.info("Entra a CornerEnrollService");
        log.info("cornerEnrollmentService es nulo? {}", cornerEnrollmentService == null);

        EnrollmentRq enrollmentRq = requestMapper.bodyMapper(dynamoSpiDto);

        log.info("EnrollmentRq: {}", enrollmentRq.toString());

        String fileName = (messageDto.getMessageDtoBatch() != null ?
                messageDto.getMessageDtoBatch().getOsIndexBatch().getFileName()
                : subject);
        log.info("FileName: {}", fileName);

        ParamVaultUpload paramVaultUpload = vaultSelectorUtil.selectorVault();

        HttpResponseWrapper httpResponseWrapper;

        if (paramFlowConfig.getVaultSyncFlow().equalsIgnoreCase(ConstantsEnum.ACTIVE_FLOW.getValue())) {
            try {
                log.info("Inicia guardado en dynamo flujo de cámaras activo");
                timeLineUtil.sendLogRq(dynamoSpiDto, rqId);
                String consent = dynamoSpiDto.getConsent();
                log.info("Consentimiento: {}", consent);
                //variable fecha consentimiento
                String effDtConsent = dynamoSpiDto.getEffDtConsent();

                log.info("Fecha consentimiento: {}", effDtConsent);

                if (ConsentEnum.N.getValue().equalsIgnoreCase(consent)) {
                    log.info("Entro en el if");
                    log.info("Consentimiento es N, creación en DirectorioAval");
                    DynamoSpiEntity dynamoSpiEntity = dynamoMapper.dynamoDtoToDynamoEntity(dynamoSpiDto);
                    //Guardado en Dynamo
                    dynamoRepository.save(dynamoSpiEntity);
                    //guardado en OpenSearch
                    openSearchSynchService.openSearchSyncEnroll(dynamoSpiEntity);
                    //envio de mensaje dto para batch
                    updateOpenSearchService.processSuccessBatchAction(messageDto);
                } else if (ConstantsEnum.S.getValue().equalsIgnoreCase(consent) && effDtConsent != null && !effDtConsent.isEmpty()) {
                    log.info("Entro en el else if");
                    log.info("Consentimiento es S, conexion con corner");

                    httpResponseWrapper = cornerEnrollmentService
                            .enrollmentKeyService(
                                    enrollmentRq,
                                    paramVaultUpload.getUrlEnrollmentVault(),
                                    vaultServicesTimeOut.getCornerEnrollmentTimeOut());

                    log.info("Respuesta de la cámara: {}", httpResponseWrapper.getResponseBody());
                    log.info("Código de respuesta: {}", httpResponseWrapper.getStatusCode());

                    MsgInformationResponse msgInformationResponse = (MsgInformationResponse)
                            Util.string2object(httpResponseWrapper.getResponseBody(), MsgInformationResponse.class);

                    log.info("MessageDto batch: {}", Util.object2String(messageDto.getMessageDtoBatch()));
                    if (messageDto.getMessageDtoBatch() != null
                            && messageDto.getMessageDtoBatch().getOsIndexBatch() != null
                            && messageDto.getMessageDtoBatch().getOsIndexBatch()
                            .getEventBatch().equalsIgnoreCase(ActionConstants.EVENT_BATCH_VAULT_SYNC.getValue())) {

                        processEnrollmentServiceRs(msgInformationResponse,
                                httpResponseWrapper.getStatusCode(),
                                dynamoSpiDto, messageDto,
                                fileName, rqId, subject);

                    } else {
                        processEnrollmentServiceRs(msgInformationResponse, httpResponseWrapper.getStatusCode(),
                                dynamoSpiDto, messageDto, enrollmentRq, "S",
                                paramVaultUpload.getUrlEnrollmentVault(), fileName, rqId, subject);
                    }
                } else {
                    log.info("El campo effDtConsent es NULL");
                }

            } catch (IOException conExp) {
                throw new ATHException(ResponseServiceEnum.ERROR_TEC_EXCEPTION.getServerStatusCode(),
                        ResponseServiceEnum.ERROR_TEC_EXCEPTION.getStatusDesc() + conExp.getMessage(),
                        ResponseServiceEnum.ERROR_TEC_EXCEPTION.getStatusCode());
            } catch (URISyntaxException | InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                timeLineUtil.sendLogRs(dynamoSpiDto, rqId);
            }
        } else {
            log.info("El flujo de sincronización no está activo");
            processEnrollmentDirAvalService(dynamoSpiDto, messageDto, rqId);
        }
    }


    /**
     * Método encargado de procesar la respuesta de la cámara y hacer el
     * respectivo redireccionamiento a sonda
     *
     * @param msgInformationResponse
     * @param httpStatusCode
     * @param dynamoSpiDto
     * @param messageDto
     * @param fileName
     */
    private void processEnrollmentServiceRs(MsgInformationResponse msgInformationResponse,
                                            int httpStatusCode,
                                            DynamoSpiDto dynamoSpiDto,
                                            MessageDto messageDto,
                                            String fileName,
                                            String rqId,
                                            String subject
    ) {
        log.info("Inicia ProcessEnrollmentServiceRs");

        if (httpStatusCode == ResponseStatusCodeEnum.PERSON_SUCCESS_STATUS_CODE.getValue()
                || (httpStatusCode == ResponseStatusCodeEnum.PERSON_CREATED_STATUS_CODE.getValue())
        ) {
            log.info("Respuesta del servicio exitosa: {}", Util.object2String(msgInformationResponse));
            updateOpenSearchService.processSuccessBatchAction(messageDto);

        } else {
            log.info("El servicio no fue exitoso");
            updateOpenSearchService.processOpensearchAction(messageDto,
                    fileName,
                    String.valueOf(httpStatusCode),
                    ConstantsEnum.ERROR_SERVICE.getValue(),
                    rqId
            );
        }
    }


    private void processEnrollmentServiceRs(MsgInformationResponse msgInformationResponse,
                                            int httpStatusCode,
                                            DynamoSpiDto dynamoSpiDto,
                                            MessageDto messageDto, EnrollmentRq enrollmentRq,
                                            String consent, String uri,
                                            String fileName, String rqId, String subject
    ) {

        if (httpStatusCode == ResponseStatusCodeEnum.PERSON_SUCCESS_STATUS_CODE.getValue()
                || (httpStatusCode == ResponseStatusCodeEnum.PERSON_CREATED_STATUS_CODE.getValue())
        ) {

            log.info("Respuesta del servicio exitosa: {}", Util.object2String(msgInformationResponse));


            log.info("Inicia guardado en dynamo");

            DynamoSpiEntity dynamoSpiEntity = dynamoMapper.dynamoDtoToDynamoEntity(dynamoSpiDto);

            log.info("Pasa mapeo dynamo: {}", Util.object2String(dynamoSpiEntity));

            if (dynamoRepository.load(dynamoSpiEntity.getId(), dynamoSpiEntity.getSk()) == null) {
                log.info("Entidad de dynamo: {}", Util.object2StringWithNulls(dynamoSpiEntity));

                dynamoRepository.save(dynamoSpiEntity);

                log.info("Finalizo guardado en dynamo");
            }

            if (updateOpenSearchService.searchKey(dynamoSpiDto) == 0) {
                log.info("Inicia sincronización con OpenSearch");

                openSearchSynchService.openSearchSyncEnroll(dynamoSpiEntity);

                log.info("Finalizo guardado opensearch ");
            }
            updateOpenSearchService.processSuccessBatchAction(messageDto);

            if (consent.equalsIgnoreCase(ConstantsEnum.S.getValue())
                    && paramFlowConfig.getConsentFlow().equalsIgnoreCase(ConstantsEnum.ACTIVE_FLOW.getValue())) {
            } else if (consent.equalsIgnoreCase(ConstantsEnum.S.getValue())) {
                saveOpensearchConsent(dynamoSpiDto, rqId, fileName);
            }

        } else {

            log.info("Respuesta del servicio no exitosa: {}", Util.object2String(msgInformationResponse));

            updateOpenSearchService.processOpensearchAction(messageDto,
                    dynamoSpiDto,
                    fileName,
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION.getStatusCode().toString(),
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION.getStatusDesc(),
                    rqId,
                    subject);


        }
    }


    private void processEnrollmentDirAvalService(
            DynamoSpiDto dynamoSpiDto,
            MessageDto messageDto, String rqUUID) {

        log.info("Inicia guardado en dynamo flujo de cámaras no activo");
        DynamoSpiEntity dynamoSpiEntity = dynamoMapper.dynamoDtoToDynamoEntity(dynamoSpiDto);

        if (dynamoRepository.load(dynamoSpiEntity.getId(), dynamoSpiEntity.getSk()) == null) {
            log.info("Entidad de dynamo a guardar: {}", Util.object2StringWithNulls(dynamoSpiEntity));
            dynamoRepository.save(dynamoSpiEntity);

        }

        if (updateOpenSearchService.searchKey(dynamoSpiDto) == 0) {

            log.info("Inicia sincronización con OpenSearch flujo de cámaras no activo");
            openSearchSynchService.openSearchSyncEnroll(dynamoSpiEntity);
            log.info("Finaliza sincronización con OpenSearch flujo de cámaras no activo");
        }

        if (updateOpenSearchService.searchBatch(dynamoSpiDto) == 0) {
            log.info("Inicia actualizacion/guardado en index_batch");
            saveOpensearchVaultSync(dynamoSpiDto, rqUUID, messageDto.getMessageDtoDynamo().getFileName());
            log.info("Finaliza actualizacion/guardado en index_batch");
        }

    }

    private void saveOpensearchConsent(DynamoSpiDto dynamoSpiDto, String rqID, String fileName) {
        log.info("Inicia guardado en opensearch de consentimiento");
        OSIndexBatch osIndexBatch = indexBatchMapper.mapEnrollmentRqToIndexBatch(dynamoSpiDto, rqID,
                ActionConstants.EVENT_BATCH_CONSENT.getValue(),
                fileName
        );
        updateOpenSearchService.addElement(osIndexBatch, IndexConstants.SONDA_INDEX);
    }

    private void saveOpensearchVaultSync(DynamoSpiDto dynamoSpiDto, String rqUUID, String fileName) {
        log.info("Inicia guardado en opensearch de sincronización");
        OSIndexBatch osIndexBatch = indexBatchMapper.mapEnrollmentRqToIndexBatch(dynamoSpiDto, rqUUID,
                ActionConstants.EVENT_BATCH_VAULT_SYNC.getValue(),
                fileName
        );
        updateOpenSearchService.addElement(osIndexBatch, IndexConstants.SONDA_INDEX);
    }
}
