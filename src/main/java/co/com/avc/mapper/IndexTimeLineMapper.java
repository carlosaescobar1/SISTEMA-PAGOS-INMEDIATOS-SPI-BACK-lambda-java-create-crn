package co.com.avc.mapper;

import co.com.ath.commons.util.Util;
import co.com.ath.opensearch.logs.constants.TypeServiceConstants;
import co.com.ath.opensearch.logs.entity.index_timeline.OSIndexTimeline;
import co.com.avc.constants.ConstantsEnum;
import co.com.avc.models.dynamo.DynamoSpiDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IndexTimeLineMapper {

    /**
     * Instancia del objeto que representa el indice index_timeline de Open search
     */
    private OSIndexTimeline osIndexTimeline;



    /**
     * Método que mapea el objeto de entrada de la solicitud de registro (Enrollment)
     * al objeto que representa el índice de time_line en Open search.
     *
     * @param dynamoSpiDto modelo que representa el request de la
     *                     petición de creación en línea.
     * @param typeService  Variable que representa el tipo de log que se quiere guardar
     *                     Request (Rq) o Response (Rs) usado para asignar la fecha
     *                     de la operación.
     * @return Objeto OSIndexTimeline con la información mapeada.
     */
    public OSIndexTimeline mapBodyToTimeLine(DynamoSpiDto dynamoSpiDto, String rqId,
                                                   TypeServiceConstants typeService) {

        osIndexTimeline.setRqId(getRqId(rqId));

        osIndexTimeline.setKeyType(getKeyType(dynamoSpiDto));

        osIndexTimeline.setKeyId(getKeyId(dynamoSpiDto));

        osIndexTimeline.setDateOperation(getDateOperation(dynamoSpiDto, typeService));

        osIndexTimeline.setCompanyId(getCompanyId(dynamoSpiDto));

        osIndexTimeline.setBankId(getBankId(dynamoSpiDto));

        osIndexTimeline.setChanel(getChanel());

        return osIndexTimeline;
    }

    private String getRqId(String rqId) {
        return rqId != null
                ? rqId
                : ConstantsEnum.EMPTY_STRING.getValue();
    }

    private String getDateOperation(DynamoSpiDto dynamoSpiDto,
                                    TypeServiceConstants typeService) {
        if (typeService.equals(TypeServiceConstants.REQUEST)) {
            return dynamoSpiDto != null
                    && dynamoSpiDto.getEffDtCreate() != null
                    ? dynamoSpiDto.getEffDtCreate()
                    : Util.createDate();
        } else {
            return Util.createDate();
        }
    }


    public OSIndexTimeline mapDynamoToTimeLine(DynamoSpiDto dynamoSpiDto, String rqUUID) {

        osIndexTimeline.setRqId(rqUUID);

        osIndexTimeline.setKeyType(getKeyType(dynamoSpiDto));

        osIndexTimeline.setKeyId(getKeyId(dynamoSpiDto));

        osIndexTimeline.setDateOperation(Util.createDate());

        osIndexTimeline.setCompanyId(getCompanyId(dynamoSpiDto));

        osIndexTimeline.setBankId(getBankId(dynamoSpiDto));

        osIndexTimeline.setChanel(getChanel());

        return osIndexTimeline;
    }


    private String getKeyType(DynamoSpiDto dynamoSpiDto) {
        return dynamoSpiDto != null
                && dynamoSpiDto.getKey() != null
                && dynamoSpiDto.getKey().getKeyType() != null
                ? dynamoSpiDto.getKey().getKeyType()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }

    private String getKeyId(DynamoSpiDto dynamoSpiDto) {
        return dynamoSpiDto != null
                && dynamoSpiDto.getKey() != null
                && dynamoSpiDto.getKey().getKeyId() != null
                ? dynamoSpiDto.getKey().getKeyId()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }

    private String getCompanyId(DynamoSpiDto dynamoSpiDto) {
        return dynamoSpiDto != null
                && dynamoSpiDto.getAcctInfo() != null
                && dynamoSpiDto.getAcctInfo().getBankId() != null
                ? dynamoSpiDto.getAcctInfo().getBankId()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }

    private String getBankId(DynamoSpiDto dynamoSpiDto) {
        return dynamoSpiDto != null
                && dynamoSpiDto.getAcctInfo() != null
                && dynamoSpiDto.getAcctInfo().getBankId() != null
                ? dynamoSpiDto.getAcctInfo().getBankId()
                : ConstantsEnum.EMPTY_STRING.getValue();
    }


    private String getChanel() {
        return ConstantsEnum.HEADER_CHANNEL.getValue();
    }



}
