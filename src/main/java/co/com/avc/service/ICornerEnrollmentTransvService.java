package co.com.avc.service;

import co.com.avc.models.MessageDto;
import co.com.avc.models.dynamo.DynamoSpiDto;

public interface ICornerEnrollmentTransvService {
    void cornerEnrollService(MessageDto messageDto, String subject, DynamoSpiDto dynamoSpiDto, String rqID, String dateOperation, String rqUUID);
}
