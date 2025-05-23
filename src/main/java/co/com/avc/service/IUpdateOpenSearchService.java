package co.com.avc.service;

import co.com.ath.opensearch.logs.constants.IndexConstants;
import co.com.avc.models.MessageDto;
import co.com.avc.models.dynamo.DynamoSpiDto;

import java.util.Map;

public interface IUpdateOpenSearchService {
    void addElement(Object document, IndexConstants indexConstant);

    void updateElement(Map<String, Object> jsonMap, String id);

    long searchKey(DynamoSpiDto dynamoSpiDto);

    long searchBatch(DynamoSpiDto dynamoSpiDto);

    void processOpensearchAction(
            MessageDto messageDto,
            DynamoSpiDto dynamoSpiDto,
            String fileName,
            String errorType,
            String errorDesc,
            String rqId,
            String subject
    );

    void saveIndexRejected(
            DynamoSpiDto dynamoSpiDto,
            String fileName,
            String errorType,
            String errorDesc,
            String rqId,
            String rqUUID
    );

    void processOpensearchAction(
            MessageDto messageDto,
            String fileName,
            String errorType,
            String errorDesc,
            String rqId
    );

    void processSuccessBatchAction(MessageDto messageDto);


    void processSuccessDirAvalAction(MessageDto messageDto,
                                     String fileName,
                                     String errorType,
                                     String errorDesc,
                                     String rqId,
                                     String rqUUID);
}
