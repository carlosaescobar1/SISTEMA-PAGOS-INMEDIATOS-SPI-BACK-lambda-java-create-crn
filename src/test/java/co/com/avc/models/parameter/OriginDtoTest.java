package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;

class OriginDtoTest {

    @Test
    void testGetMobileBanking() {
        OriginDto originDto = new OriginDto();
        originDto.setMobileBanking("mobileBankingValue");
        originDto.setVirtualBanking("virtualBankingValue");
        originDto.setOffices("officesValue");

        assertEquals("mobileBankingValue", originDto.getMobileBanking());
        assertEquals("virtualBankingValue", originDto.getVirtualBanking());
        assertEquals("officesValue", originDto.getOffices());
    }

}