package co.com.avc.mapper;

import co.com.ath.commons.util.Util;
import co.com.ath.opensearch.logs.entity.index_rejected.OSIndexRejected;
import co.com.avc.models.dynamo.DynamoSpiDto;

public class IndexRejectedMapper {

    private final OSIndexRejected osIndexRejected = new OSIndexRejected();

    public OSIndexRejected indexRejected(DynamoSpiDto dynamoSpiDto, String fileName, String errorType, String errorDesc, String rqId, String UUID) {

        osIndexRejected.setFileName(fileName);
        osIndexRejected.setBankId(dynamoSpiDto.getAcctInfo().getBankId());
        osIndexRejected.setKey(dynamoSpiDto.getKey().getKeyId());
        osIndexRejected.setRqId(rqId);
        osIndexRejected.setRqUUID(UUID);
        osIndexRejected.setErrorType(errorType);
        osIndexRejected.setErrorDesc(errorDesc);
        osIndexRejected.setErrorDate(Util.createDate());

        return osIndexRejected;
    }

}
