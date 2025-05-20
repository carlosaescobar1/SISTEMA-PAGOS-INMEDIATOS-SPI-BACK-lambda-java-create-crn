package co.com.avc.util;

import co.com.ath.opensearch.logs.constants.ActionConstants;
import co.com.ath.opensearch.logs.constants.TimeStampEnum;
import co.com.ath.opensearch.logs.constants.TypeServiceConstants;
import co.com.ath.opensearch.logs.service.OpensearchLogService;
import co.com.avc.mapper.IndexTimeLineMapper;
import co.com.avc.models.dynamo.DynamoSpiDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TimeLineUtil {

    /**
     * Dependencia inyectada por medio del constructor usada
     * para el envío de los a traves de mensajería SNS
     */
    private final OpensearchLogService opensearchLogService;

    /**
     * Dependencia inyectada por medio del constructor usada
     * para el mapeo de los mensajes que serán enviados al
     * índice index_timeline en open search.
     */
    private final IndexTimeLineMapper indexTimeLineMapper;

    /**
     * Atributo seteado a traves del constructor que contiene
     * el ARN del SNS vinculado al guardado de registros en
     * Open Search.
     */
    private final SnsSelectorUtil snsSelectorUtil;


    /**
     * Método que envía al SNS el registro inicial del proceso de
     * creación en línea (Petición de usuario para la creación de una
     * llave)
     * <p>
     * Obtiene los parámetros en la clase MigrationKeyServiceImpl
     * método migrateKeys.
     *
     * @param dynamoSpiDto modelo que representa el request de la
     *                     petición de creación en línea.
     */
    public void sendLogRq(DynamoSpiDto dynamoSpiDto, String rqId) {

        opensearchLogService.sendSNSOpenSearchLogs(indexTimeLineMapper.mapBodyToTimeLine(dynamoSpiDto,
                        rqId,
                        TypeServiceConstants.REQUEST),
                snsSelectorUtil.selectSNS(dynamoSpiDto.getAcctInfo().getBankId()),
                ActionConstants.ONLINE_ENROLL,
                TimeStampEnum.FED_ENROLLMENT_REQUEST);

    }

    /**
     * Método que envía al SNS el registro final del proceso de
     * creación en línea (Respuesta al usuario para la creación de una
     * llave)
     * <p>
     * Obtiene los parámetros en la clase Handler
     * método redirect.
     *
     * @param dynamoSpiDto modelo que representa el request de la
     *                     petición de creación en línea.
     */
    public void sendLogRs(DynamoSpiDto dynamoSpiDto, String rqUUID) {

        opensearchLogService.sendSNSOpenSearchLogs(indexTimeLineMapper.mapDynamoToTimeLine(dynamoSpiDto, rqUUID),
                snsSelectorUtil.selectSNS(dynamoSpiDto.getAcctInfo().getBankId()),
                ActionConstants.ONLINE_ENROLL,
                TimeStampEnum.CUST_ENROLLMENT_RESPONSE);

    }




}
