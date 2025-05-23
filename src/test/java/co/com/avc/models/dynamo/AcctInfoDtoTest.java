package co.com.avc.models.dynamo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AcctInfoDtoTest {

    @Test
    void testAcctInfoDto() {

        AcctInfoDto acctInfoDto = new AcctInfoDto();

        String acctType = "accType";
        String acctId = "acctId";
        String bankId = "bankId";

        acctInfoDto.setAcctType(acctType);
        acctInfoDto.setAcctId(acctId);
        acctInfoDto.setBankId(bankId);

        assertEquals(acctType, acctInfoDto.getAcctType());
        assertEquals(acctId, acctInfoDto.getAcctId());
        assertEquals(bankId, acctInfoDto.getBankId());
        assertEquals(acctInfoDto.hashCode(), acctInfoDto.hashCode());

    }

}