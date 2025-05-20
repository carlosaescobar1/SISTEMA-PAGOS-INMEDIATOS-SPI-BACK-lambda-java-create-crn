package co.com.avc.models.dynamo;

import org.junit.jupiter.api.Test;

class CustIdentDtoTest {

    @Test
    void testCustIdentDto() {

        CustIdentDto custIdentDto = new CustIdentDto();

        String custIdentType = "custIdentType";
        String custIdentNum = "custIdentNum";

        custIdentDto.setCustIdentType(custIdentType);
        custIdentDto.setCustIdentNum(custIdentNum);

        assertEquals(custIdentType, custIdentDto.getCustIdentType());
        assertEquals(custIdentNum, custIdentDto.getCustIdentNum());

    }

}