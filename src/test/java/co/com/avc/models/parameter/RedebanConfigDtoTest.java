package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class RedebanConfigDtoTest {
    
    @Test
    void testSetOriginDtoList() {
        RedebanConfigDto redebanConfigDto = new RedebanConfigDto();
        redebanConfigDto.setOriginDtoList(null);
        assertNull(redebanConfigDto.getOriginDtoList());

    }
}