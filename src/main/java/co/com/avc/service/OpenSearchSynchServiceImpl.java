package co.com.avc.service;

import co.com.ath.opensearch.logs.constants.ActionConstants;
import co.com.ath.opensearch.sync.constants.SyncActionEnum;
import co.com.ath.opensearch.sync.service.IBlackListService;
import co.com.ath.opensearch.sync.service.IOpensearchService;
import co.com.avc.entity.DynamoSpiEntity;
import co.com.avc.mapper.IOpenSearchMapper;
import co.com.avc.mapper.IOpenSearchMapperImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.client.opensearch.OpenSearchClient;

@Slf4j
@AllArgsConstructor
public class OpenSearchSynchServiceImpl implements IOpenSearchSynchService{
    /**
     * Instancia del servicio de opensearch de la librería de
     * sincronización
     */
    private final IOpensearchService opensearchService;

    /**
     * Instancia del cliente de opensearch
     */
    private final OpenSearchClient openSearchClient;

    /**
     * Instancia del servicio de las listas negras de
     * la librería de sincronización
     */
    private final IBlackListService blackListService;

    private final IOpenSearchMapper openSearchMapper = new IOpenSearchMapperImpl();

    /**
     * Método que hace la sincronización con las llaves de dynamo
     * al index key de opensearch
     *
     * @param dynamoSpiEntity Entidad a guardar
     */
    @Override
    public void openSearchSyncEnroll(DynamoSpiEntity dynamoSpiEntity) {
        log.info("Comenzó la sincronización de la entidad: {}", dynamoSpiEntity);
        opensearchService.syncKey(openSearchMapper.dynamoEntityToIndexKey(dynamoSpiEntity, ActionConstants.EVENT_MASIVE_ENROLL.getValue()),
                SyncActionEnum.INSERT);
    }

    /**
     * Método que valida la coincidencia de una llave con
     * las listas negras
     *
     * @param key Llave a consultar
     */
    @Override
    public void openSearchSyncBlackListValidate(String key) {
        log.info("Comenzó a validar esta llave en las listas negras: {}", key);
        blackListService.validateBlackListKey(openSearchClient, key);
    }
}
