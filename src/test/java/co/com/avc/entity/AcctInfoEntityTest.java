package co.com.avc.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AcctInfoEntityTest {

    @Test
    void testAcctInfoEntity(){

        AcctInfoEntity acctInfoEntity = new AcctInfoEntity();

        String acctType = "acctType";
        String acctId = "acctId";
        String bankId = "bankId";
        String result = "AcctInfoEntity(acctType=acctType, acctId=acctId, bankId=bankId)";

        acctInfoEntity.setAcctType(acctType);
        acctInfoEntity.setAcctId(acctId);
        acctInfoEntity.setBankId(bankId);

        assertEquals(acctType,acctInfoEntity.getAcctType());
        assertEquals(acctId,acctInfoEntity.getAcctId());
        assertEquals(bankId,acctInfoEntity.getBankId());
        assertEquals(result,acctInfoEntity.toString());

    }
}