package co.com.avc.repository;

import co.com.ath.commons.util.ParameterStoreUtil;
import co.com.ath.commons.util.Util;
import co.com.avc.constants.ParameterStoreEnum;
import co.com.avc.models.parameter.*;
import lombok.extern.slf4j.Slf4j;

import java.io.StringWriter;
import java.util.Map;

/**
 * ParameterStoreRepository
 * <p>
 * Esta clase recupera los parámetros almacenados en AWS ParameterStore
 * necesarios para la creación y actualización de llaves dentro del
 * directorio AVAL - Base de datos DynamoDB.
 * <p>
 * Desarrollo ATH - AvalPay Center
 * <p>
 * Creado el : 10 de Noviembre de 2024
 * <p>
 * Autor: Luis F. Herreño Mateus
 * <p>
 * Requerimiento: Migración AvalPay Center
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 * <p>
 * Clase encargada de conectar con el parameter store
 *
 * @author Luis F Herreno
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class ParameterStoreRepository {

    StringWriter errors = new StringWriter();

    /**
     * Método que recupera los valores de los parámetros almacenados en AWS ParameterStore
     *
     * @return ParameterStoreDto Objeto que almacena los valores recuperados de AWS ParameterStore
     */
    public ParameterStoreDto getParameters() {
        ParameterStoreDto parameterStoreDto = new ParameterStoreDto();
        log.info("Inicia consulta de parámetros");

        try {
            Map<String, String> generalParameter = ParameterStoreUtil
                    .getParameters(ParameterStoreEnum.PARAMETER_GENERAL_PATH_URL.getValue());

            if (generalParameter == null || generalParameter.isEmpty()) {
                log.error("No se encontraron parámetros en el Parameter Store");
                return parameterStoreDto;
            }

            parameterStoreDto.setArnSnsOpenSearch((ArnSnsOpenSearch)
                    Util.string2object(generalParameter.get(ParameterStoreEnum.PARAM_ARN_SNS_LOGS_OPEN_SEARCH.getValue()),
                            ArnSnsOpenSearch.class));

            parameterStoreDto.setVaultServicesTimeOut((VaultServicesTimeOut)
                    Util.string2object(generalParameter.get(ParameterStoreEnum.PARAM_VAULT_SERVICE_TIMEOUT.getValue()),
                            VaultServicesTimeOut.class));

            parameterStoreDto.setArnSecretOpenSearch(
                    generalParameter.get(ParameterStoreEnum.PARAM_ARN_SECRET_OPEN_SEARCH.getValue())
            );

            parameterStoreDto.setParamDynamo((ParamDynamo)
                    Util.string2object(generalParameter.get(ParameterStoreEnum.PARAM_DYNAMO.getValue()),
                            ParamDynamo.class));

            parameterStoreDto.setParamFlowConfig((ParamFlowConfig)
                    Util.string2object(generalParameter.get(ParameterStoreEnum.PARAM_FLOW_CONFIG.getValue()),
                            ParamFlowConfig.class));

            String maxRetryBatch = generalParameter.get(ParameterStoreEnum.PARAM_MAX_RETRY_BATCH.getValue());
            parameterStoreDto.setMaxRetryBatch(maxRetryBatch != null ? Integer.parseInt(maxRetryBatch) : 0);

            parameterStoreDto.setRegion(generalParameter.get(ParameterStoreEnum.PARAM_REGION.getValue()));

            parameterStoreDto.setParamActiveVault((ParamActiveVault)
                    Util.string2object(generalParameter.get(ParameterStoreEnum.PARAM_JSON_VAULT.getValue()),
                            ParamActiveVault.class));

            String blackListScore = generalParameter.get(ParameterStoreEnum.PARAM_BLACK_LIST_MAX_SCORE.getValue());
            parameterStoreDto.setBlackListScore(blackListScore != null ? Double.parseDouble(blackListScore) : 0.0);

            log.info("Parámetros obtenidos correctamente");
        } catch (Exception e) {
            log.error("Error al obtener los parámetros del Parameter Store", e);
        }

        return parameterStoreDto;
    }
}
