package co.com.avc.entity;

import org.junit.jupiter.api.Test;

class CustInfEntityTest {

    @Test
    void testCustInfEntity(){

        CustInfEntity custInfEntity = new CustInfEntity();
        CustIdentEntity custIdent = new CustIdentEntity();

        String custFirstName = "custFirstName";
        String custSecondName = "custSecondName";
        String custFirstLastName = "custFirstLastName";
        String custSecondLastName = "custSecondLastName";
        String custLegalName = "custLegalName";
        String custIdentType = "custIdentType";
        String custIdentNum = "custIdentNum";
        String result = "CustInfEntity(custFirstName=custFirstName," +
                " custSecondName=custSecondName," +
                " custFirstLastName=custFirstLastName," +
                " custSecondLastName=custSecondLastName," +
                " custLegalName=custLegalName," +
                " custIdent=CustIdentEntity(custIdentType=custIdentType," +
                " custIdentNum=custIdentNum))";

        custInfEntity.setCustFirstName(custFirstName);
        custInfEntity.setCustSecondName(custSecondName);
        custInfEntity.setCustFirstLastName(custFirstLastName);
        custInfEntity.setCustSecondLastName(custSecondLastName);
        custInfEntity.setCustLegalName(custLegalName);
        custIdent.setCustIdentType(custIdentType);
        custIdent.setCustIdentNum(custIdentNum);
        custInfEntity.setCustIdent(custIdent);

        assertEquals(custFirstName,custInfEntity.getCustFirstName());
        assertEquals(custSecondName,custInfEntity.getCustSecondName());
        assertEquals(custFirstLastName,custInfEntity.getCustFirstLastName());
        assertEquals(custSecondLastName,custInfEntity.getCustSecondLastName());
        assertEquals(custSecondLastName,custInfEntity.getCustSecondLastName());
        assertEquals(custLegalName,custInfEntity.getCustLegalName());
        assertEquals(custIdentType,custIdent.getCustIdentType());
        assertEquals(custIdentNum,custIdent.getCustIdentNum());
        assertEquals(result,custInfEntity.toString());
    }

}