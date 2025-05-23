package co.com.avc.mapper;

import co.com.ath.commons.util.ATHException;
import co.com.ath.commons.util.Util;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.constants.ResponseServiceEnum;
import co.com.avc.cornerconn.models.Key;
import co.com.avc.cornerconn.models.PaymentMethod;
import co.com.avc.cornerconn.models.Person;
import co.com.avc.cornerconn.models.enrollment.EnrollmentRq;
import co.com.avc.models.MessageDto;
import co.com.avc.models.MessageDtoBatch;
import co.com.avc.models.MessageDtoDynamo;
import co.com.avc.models.SqsDto;
import co.com.avc.models.dynamo.DynamoSpiDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestMapper {

    /**
     * Instandia del objeto de dynamo
     */
    private DynamoSpiDto dynamoSpiDto = new DynamoSpiDto();

    /**
     * @param sqsDto
     * @return
     */
    public MessageDto messageDtoMapper(SqsDto sqsDto) {
        try {
            MessageDto messageDto = new MessageDto();
            log.info("SQS DTO: {}", sqsDto.toString());
            if (sqsDto.getSubject() != null && sqsDto.getSubject().equalsIgnoreCase(ConstantsEnum.SUBJECT_BATCH.getValue())) {
                log.info("messageDtoMapper ingreso if sonda");
                log.info("Message recibido: {}", sqsDto.getMessage());
                Object result = Util.string2object(sqsDto.getMessage(), MessageDtoBatch.class);
                log.info("result: {}", result.toString());
                if (result instanceof MessageDtoBatch) {
                    MessageDtoBatch messageDtoBatch = (MessageDtoBatch) result;
                    messageDto.setMessageDtoBatch(messageDtoBatch);
                }
            } else if (sqsDto.getSubject() != null && sqsDto.getSubject().contains(ConstantsEnum.SUBJECT_MIGRATION.getValue())) {
                log.info("messageDtoMapper ingreso if migración");
                MessageDtoDynamo messageDtoDynamo = new MessageDtoDynamo();
                messageDtoDynamo.setFileName(sqsDto.getSubject());
                Object result = Util.string2objectWhitNulls(sqsDto.getMessage(), DynamoSpiDto.class);
                if (result instanceof DynamoSpiDto dynamoSpiDto1) {
                    messageDtoDynamo.setDynamoSpiDto(dynamoSpiDto1);
                }
                messageDto.setMessageDtoDynamo(messageDtoDynamo);
            }
            log.info("messageDtoMapper MessageRecibido: {}", messageDto.toString());
            return messageDto;
        } catch (NullPointerException e) {
            throw new ATHException(ResponseServiceEnum.ERROR_TEC_EXCEPTION.getServerStatusCode(),
                    ResponseServiceEnum.ERROR_TEC_EXCEPTION.getStatusDesc(), ResponseServiceEnum.ERROR_TEC_EXCEPTION.getStatusCode());
        }

    }

    public DynamoSpiDto redirectDynamoData(MessageDto messageDto, String subject, String dateOperation) {

        if (subject.equalsIgnoreCase(ConstantsEnum.SUBJECT_BATCH.getValue())) {
            dynamoSpiDto = (DynamoSpiDto) Util.string2objectWhitNulls(
                    messageDto.getMessageDtoBatch().getOsIndexBatch().getRqServiceObject(),
                    DynamoSpiDto.class);
        } else {
            dynamoSpiDto = messageDto.getMessageDtoDynamo().getDynamoSpiDto();
        }
        dynamoSpiDto.setVaultNameRec(ConstantsEnum.CORNER.getValue());
        dynamoSpiDto.setEffDtCreate(dateOperation);

        log.info("redirectDynamoData: " + Util.object2StringWithNulls(dynamoSpiDto));

        return dynamoSpiDto;
    }

    public EnrollmentRq bodyMapper(DynamoSpiDto dynamoSpiDto) {
        try {

            if (dynamoSpiDto == null) {
                throw new ATHException(ResponseServiceEnum.ERROR_TEC_EXCEPTION.getServerStatusCode(),
                        ResponseServiceEnum.ERROR_TEC_EXCEPTION.getStatusDesc(), ResponseServiceEnum.ERROR_TEC_EXCEPTION.getStatusCode());
            }

            EnrollmentRq enrollmentRq = new EnrollmentRq();
            log.info("bodyMapper ingreso");
            enrollmentRq.setPerson(getPerson(dynamoSpiDto));
            enrollmentRq.setKey(getKey(dynamoSpiDto));
            enrollmentRq.setPaymentMethod(getPaymentMethod(dynamoSpiDto));
            enrollmentRq.setDescription("");

            log.info("Enrollment RQ: " + Util.object2String(enrollmentRq));
            return enrollmentRq;
        } catch (Exception e) {
            log.error("Error construyendo el body: ", e);
            throw e;
        }
    }


    private Person getPerson(DynamoSpiDto dynamoSpiDto) {
        try {
            Person person = new Person();
            person.setFirstName(dynamoSpiDto.getCustInf().getCustFirstName());
            person.setSecondName(dynamoSpiDto.getCustInf().getCustSecondName());
            person.setFirstSurName(dynamoSpiDto.getCustInf().getCustFirstLastName());
            person.setSecondSurName(dynamoSpiDto.getCustInf().getCustSecondLastName());
            person.setTypePerson(dynamoSpiDto.getCustType());
            person.setBusinessName("");
            person.setDocumentType(dynamoSpiDto.getCustInf().getCustIdent().getCustIdentType());
            person.setDocumentNumber(dynamoSpiDto.getCustInf().getCustIdent().getCustIdentNum());

            return person;
        } catch (Exception e) {
            log.error("Error construyendo el body: ", e);
            throw e;
        }
    }

    private PaymentMethod getPaymentMethod(DynamoSpiDto dynamoSpiDto) {
        try {
            PaymentMethod paymentMethod = new PaymentMethod();
            paymentMethod.setTypePaymentAcc(dynamoSpiDto.getAcctInfo().getAcctType());
            paymentMethod.setAccountNumber(dynamoSpiDto.getAcctInfo().getAcctId());
            return paymentMethod;
        } catch (Exception e) {
            log.error("Error construyendo el body: ", e);
            throw e;
        }
    }

    private Key getKey(DynamoSpiDto dynamoSpiDto) {
        try {
            Key key = new Key();
            key.setKeyType(dynamoSpiDto.getKey().getKeyType());
            key.setValueKey(dynamoSpiDto.getKey().getKeyId());
            return key;
        } catch (Exception e) {
            log.error("Error construyendo el body: ", e);
            throw e;
        }
    }

}
