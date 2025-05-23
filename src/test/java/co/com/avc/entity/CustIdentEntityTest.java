package co.com.avc.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustIdentEntityTest {

    @Test
    void testCustIdentEntity(){

        CustIdentEntity custIdentEntity = new CustIdentEntity();

        String custIdentType = "custIdentType";
        String custIdentNum = "custIdentNum";
        String result = "CustIdentEntity(custIdentType=custIdentType, custIdentNum=custIdentNum)";

        custIdentEntity.setCustIdentType(custIdentType);
        custIdentEntity.setCustIdentNum(custIdentNum);

        assertEquals(custIdentType,custIdentEntity.getCustIdentType());
        assertEquals(custIdentNum,custIdentEntity.getCustIdentNum());
        assertEquals(result,custIdentEntity.toString());
    }

}