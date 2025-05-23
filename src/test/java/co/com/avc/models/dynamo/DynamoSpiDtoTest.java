package co.com.avc.models.dynamo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DynamoSpiDtoTest {

    @Test
    void testDynamoSpiDto() {

        DynamoSpiDto dynamoSpiDto = new DynamoSpiDto();
        String keyType = "keyType";
        String keyId = "keyId";
        KeyDto key = new KeyDto(keyType, keyId);
        CustInfDto custInf = new CustInfDto();
        AcctInfoDto acctInfo = new AcctInfoDto();

        String id = "id";
        String sk = "sk";
        String custType = "custType";
        String identSerialNum = "identSerialNum";
        String effDtCreate = "effDtCreate";
        String effDtModify = "effDtModify";
        String statusKey = "statusKey";
        String preferredIndicator = "preferredIndicator";
        String vaultNameRec = "vaultNameRec";
        String consent = "consent";
        String signerAlias = "signerAlias";

        key.setKeyType(keyType);
        custInf.setCustFirstName("custFirstName");
        acctInfo.setAcctType("acctType");
        dynamoSpiDto.setId(id);
        dynamoSpiDto.setSk(sk);
        dynamoSpiDto.setCustType(custType);
        dynamoSpiDto.setIdentSerialNum(identSerialNum);
        dynamoSpiDto.setEffDtCreate(effDtCreate);
        dynamoSpiDto.setEffDtModify(effDtModify);
        dynamoSpiDto.setStatusKey(statusKey);
        dynamoSpiDto.setPreferredIndicator(preferredIndicator);
        dynamoSpiDto.setVaultNameRec(vaultNameRec);
        dynamoSpiDto.setConsent(consent);
        dynamoSpiDto.setSignerAlias(signerAlias);
        dynamoSpiDto.setKey(key);
        dynamoSpiDto.setCustInf(custInf);
        dynamoSpiDto.setAcctInfo(acctInfo);

        assertEquals(id, dynamoSpiDto.getId());
        assertEquals(sk, dynamoSpiDto.getSk());
        assertEquals(custType, dynamoSpiDto.getCustType());
        assertEquals(identSerialNum, dynamoSpiDto.getIdentSerialNum());
        assertEquals(effDtCreate, dynamoSpiDto.getEffDtCreate());
        assertEquals(effDtModify, dynamoSpiDto.getEffDtModify());
        assertEquals(statusKey, dynamoSpiDto.getStatusKey());
        assertEquals(preferredIndicator, dynamoSpiDto.getPreferredIndicator());
        assertEquals(vaultNameRec, dynamoSpiDto.getVaultNameRec());
        assertEquals(consent, dynamoSpiDto.getConsent());
        assertEquals(signerAlias, dynamoSpiDto.getSignerAlias());
        assertNotNull(dynamoSpiDto.getKey());
        assertNotNull(dynamoSpiDto.getCustInf());
        assertNotNull(dynamoSpiDto.getAcctInfo());

    }

}