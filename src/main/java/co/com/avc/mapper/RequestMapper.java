package co.com.avc.mapper;

import co.com.ath.commons.util.Util;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.models.MessageDto;
import co.com.avc.models.MessageDtoBatch;
import co.com.avc.models.MessageDtoDynamo;
import co.com.avc.models.SqsDto;
import co.com.avc.models.dynamo.DynamoSpiDto;
import co.com.avc.cornerconn.models.*;
import co.com.avc.cornerconn.models.enrollment.EnrollmentRq;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestMapper {

    /**
     * Instancia del objeto del body de redeban
     */
    private final EnrollmentRq enrollmentRq = new EnrollmentRq();

    /**
     * Instandia del objeto de dynamo
     */
    private DynamoSpiDto dynamoSpiDto = new DynamoSpiDto();

    /**
     * @param sqsDto
     * @return
     */
    public MessageDto messageDtoMapper(SqsDto sqsDto) {
        MessageDto messageDto = new MessageDto();
        if (sqsDto.getSubject().equalsIgnoreCase(ConstantsEnum.SUBJECT_BATCH.getValue())) {
            log.info("messageDtoMapper ingreso if sonda");
            MessageDtoBatch messageDtoBatch = (MessageDtoBatch) Util.string2objectWhitNulls(sqsDto.getMessage(), MessageDtoBatch.class);
            messageDto.setMessageDtoBatch(messageDtoBatch);
        } else if (sqsDto.getSubject().contains(ConstantsEnum.SUBJECT_MIGRATION.getValue())) {
            log.info("messageDtoMapper ingreso if migración");
            MessageDtoDynamo messageDtoDynamo = new MessageDtoDynamo();
            messageDtoDynamo.setFileName(sqsDto.getSubject());
            DynamoSpiDto dynamoSpiDto1 = (DynamoSpiDto) Util.string2objectWhitNulls(sqsDto.getMessage(), DynamoSpiDto.class);
            messageDtoDynamo.setDynamoSpiDto(dynamoSpiDto1);
            messageDto.setMessageDtoDynamo(messageDtoDynamo);
        }
        log.info("messageDtoMapper: {}", Util.object2StringWithNulls(messageDto));
        return messageDto;
    }

    public DynamoSpiDto redirectDynamoData(MessageDto messageDto, String subject, String dateOperation) {

        if (subject.equalsIgnoreCase(ConstantsEnum.SUBJECT_BATCH.getValue())) {
            dynamoSpiDto = (DynamoSpiDto) Util.string2objectWhitNulls(
                    messageDto.getMessageDtoBatch().getOsIndexBatch().getRqServiceObject(),
                    DynamoSpiDto.class);
        } else {
            dynamoSpiDto = messageDto.getMessageDtoDynamo().getDynamoSpiDto();
        }
        dynamoSpiDto.setVaultNameRec(ConstantsEnum.REDEBAN.getValue());
        dynamoSpiDto.setEffDtCreate(dateOperation);

        log.info("redirectDynamoData: " + Util.object2StringWithNulls(dynamoSpiDto));

        return dynamoSpiDto;
    }

    public EnrollmentRq bodyMapper(DynamoSpiDto dynamoSpiDto){
        enrollmentRq.setPerson(getPerson(dynamoSpiDto));
        enrollmentRq.setKey(getKey(dynamoSpiDto));
        enrollmentRq.setPaymentMethod(getPaymentMehtod(dynamoSpiDto));
        enrollmentRq.setDescription("");

        return enrollmentRq;
    }


    private Person getPerson(DynamoSpiDto dynamoSpiDto){
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
    }

    private PaymentMethod getPaymentMehtod(DynamoSpiDto dynamoSpiDto){
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setTypePaymentAcc(dynamoSpiDto.getAcctInfo().getAcctType());
        paymentMethod.setAccountNumber(dynamoSpiDto.getAcctInfo().getAcctId());

        return paymentMethod;
    }

    private Key  getKey(DynamoSpiDto dynamoSpiDto){
        Key key = new Key();
        key.setKeyType(dynamoSpiDto.getAcctInfo().getAcctType());
        key.setValueKey(dynamoSpiDto.getAcctInfo().getAcctId());
        return key;
    }

}
