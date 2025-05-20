package co.com.avc.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DynamoSpiEntityTest {

    @Test
    void testDynamoSpiEntity() {

        DynamoSpiEntity dynamoSpiEntity = new DynamoSpiEntity();
        KeyEntity key = new KeyEntity();
        CustInfEntity custInf = new CustInfEntity();
        AcctInfoEntity acctInfo = new AcctInfoEntity();

        String id = "id";
        String sk = "sk";
        String keyType = "keyType";
        String custFirstName = "custFirstName";
        String custType = "custType";
        String identSerialNum = "identSerialNum";
        String acctType = "accType";
        String effDtCreate = "effDtCreate";
        String effDtModify = "effDtModify";
        String statusKey = "statusKey";
        String preferredIndicator = "preferredIndicator";
        String vaultNameRec = "vaultNameRec";
        String consent = "consent";
        String signerAlias = "signerAlias";

        dynamoSpiEntity.setId(id);
        dynamoSpiEntity.setSk(sk);
        key.setKeyType(keyType);
        dynamoSpiEntity.setKey(key);
        custInf.setCustFirstName(custFirstName);
        dynamoSpiEntity.setCustInf(custInf);
        dynamoSpiEntity.setCustType(custType);
        dynamoSpiEntity.setIdentSerialNum(identSerialNum);
        acctInfo.setAcctType(acctType);
        dynamoSpiEntity.setAcctInfo(acctInfo);
        dynamoSpiEntity.setEffDtCreate(effDtCreate);
        dynamoSpiEntity.setEffDtModify(effDtModify);
        dynamoSpiEntity.setStatusKey(statusKey);
        dynamoSpiEntity.setPreferredIndicator(preferredIndicator);
        dynamoSpiEntity.setVaultNameRec(vaultNameRec);
        dynamoSpiEntity.setConsent(consent);
        dynamoSpiEntity.setSignerAlias(signerAlias);

        assertEquals(id, dynamoSpiEntity.getId());
        assertEquals(sk, dynamoSpiEntity.getSk());
        assertEquals(keyType, key.getKeyType());
        assertEquals(custFirstName, custInf.getCustFirstName());
        assertEquals(custInf, dynamoSpiEntity.getCustInf());
        assertEquals(custType, dynamoSpiEntity.getCustType());
        assertEquals(identSerialNum, dynamoSpiEntity.getIdentSerialNum());
        assertEquals(acctType, acctInfo.getAcctType());
        assertEquals(acctInfo, dynamoSpiEntity.getAcctInfo());
        assertEquals(effDtCreate, dynamoSpiEntity.getEffDtCreate());
        assertEquals(effDtModify, dynamoSpiEntity.getEffDtModify());
        assertEquals(statusKey, dynamoSpiEntity.getStatusKey());
        assertEquals(preferredIndicator, dynamoSpiEntity.getPreferredIndicator());
        assertEquals(vaultNameRec, dynamoSpiEntity.getVaultNameRec());
        assertEquals(consent, dynamoSpiEntity.getConsent());
        assertEquals(signerAlias, dynamoSpiEntity.getSignerAlias());
        assertNotNull(dynamoSpiEntity.getKey());
    }

}