package co.com.avc.models.dynamo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustInfDtoTest {

    @Test
    void testCustInfDto(){

        CustInfDto custInfDto = new CustInfDto();
        CustIdentDto custIdent = new CustIdentDto();

        String custFirstName = "custFirstName";
        String custSecondName = "custSecondName";
        String custFirstLastName = "custFirstLastName";
        String custSecondLastName = "custSecondLastName";
        String custLegalName = "custLegalName";
        String custIdentNum = "custIdentNum";

        custIdent.setCustIdentNum(custIdentNum);
        custInfDto.setCustFirstName(custFirstName);
        custInfDto.setCustSecondName(custSecondName);
        custInfDto.setCustFirstLastName(custFirstLastName);
        custInfDto.setCustSecondLastName(custSecondLastName);
        custInfDto.setCustLegalName(custLegalName);
        custInfDto.setCustIdent(custIdent);

        assertEquals(custIdentNum,custIdent.getCustIdentNum());
        assertEquals(custFirstName,custInfDto.getCustFirstName());
        assertEquals(custSecondName,custInfDto.getCustSecondName());
        assertEquals(custFirstLastName,custInfDto.getCustFirstLastName());
        assertEquals(custSecondLastName,custInfDto.getCustSecondLastName());
        assertEquals(custLegalName,custInfDto.getCustLegalName());
        assertNotNull(custInfDto.getCustIdent().toString());

    }

}