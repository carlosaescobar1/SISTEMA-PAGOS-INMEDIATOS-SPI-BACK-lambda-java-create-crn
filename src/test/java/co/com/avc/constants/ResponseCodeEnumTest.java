package co.com.avc.constants;

import org.junit.jupiter.api.Test;

class ResponseCodeEnumTest {

    @Test
    void testGetValue() {
        assertEquals("NT00", ResponseCodeEnum.RED_PERSON_SUCCESS_STATUS_CODE.getValue());
        assertEquals("U000", ResponseCodeEnum.RED_PERSON_CREATED_STATUS_CODE.getValue());
        assertEquals("U808", ResponseCodeEnum.RED_PERSON_ERROR_CREATED_STATUS_CODE.getValue());
        assertEquals("NT40", ResponseCodeEnum.RED_PERSON_ERROR_BODY_STATUS_CODE.getValue());
    }

    @Test
    void testResponseCodeEnum() {
        assertEquals(4, ResponseCodeEnum.values().length);
        assertEquals(ResponseCodeEnum.RED_PERSON_SUCCESS_STATUS_CODE, ResponseCodeEnum.valueOf("RED_PERSON_SUCCESS_STATUS_CODE"));
        assertEquals(ResponseCodeEnum.RED_PERSON_CREATED_STATUS_CODE, ResponseCodeEnum.valueOf("RED_PERSON_CREATED_STATUS_CODE"));
        assertEquals(ResponseCodeEnum.RED_PERSON_ERROR_CREATED_STATUS_CODE, ResponseCodeEnum.valueOf("RED_PERSON_ERROR_CREATED_STATUS_CODE"));
        assertEquals(ResponseCodeEnum.RED_PERSON_ERROR_BODY_STATUS_CODE, ResponseCodeEnum.valueOf("RED_PERSON_ERROR_BODY_STATUS_CODE"));
    }

}