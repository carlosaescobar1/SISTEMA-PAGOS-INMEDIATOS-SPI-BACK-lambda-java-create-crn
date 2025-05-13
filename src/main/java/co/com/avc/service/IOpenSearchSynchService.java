package co.com.avc.service;

import co.com.avc.entity.DynamoSpiEntity;

public interface IOpenSearchSynchService {
    void openSearchSyncEnroll(DynamoSpiEntity dynamoSpiEntity);
    void openSearchSyncBlackListValidate(String key);
}
