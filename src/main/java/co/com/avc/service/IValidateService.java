package co.com.avc.service;

import co.com.avc.models.dynamo.DynamoSpiDto;

public interface IValidateService {
    boolean validateBannedKeys(DynamoSpiDto dynamoSpiDto, String fileName, String rqId, String rqUUID);
}
