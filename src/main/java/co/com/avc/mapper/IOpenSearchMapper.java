package co.com.avc.mapper;

import co.com.ath.opensearch.logs.entity.index_key.OSIndexKey;
import co.com.avc.entity.DynamoSpiEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IOpenSearchMapper {

    IOpenSearchMapper INSTANCE = Mappers.getMapper(IOpenSearchMapper.class);
    OSIndexKey dynamoEntityToIndexKey(DynamoSpiEntity dynamoSpiDto, String flowService);

}
