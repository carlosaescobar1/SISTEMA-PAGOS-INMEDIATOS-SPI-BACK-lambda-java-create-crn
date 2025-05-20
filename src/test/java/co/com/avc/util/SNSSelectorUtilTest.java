package co.com.avc.util;

import co.com.avc.constants.BankIdEnum;
import co.com.avc.models.parameter.ArnSnsOpenSearch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SNSSelectorUtilTest {

    private ArnSnsOpenSearch arnSnsOpenSearch = new ArnSnsOpenSearch();


    @Test
    void snsSelector() {
        String test = "test";

        arnSnsOpenSearch.setArnSnsOpenSearchBAVV(test);
        arnSnsOpenSearch.setArnSnsOpenSearchBBOG(test);
        arnSnsOpenSearch.setArnSnsOpenSearchBOCC(test);
        arnSnsOpenSearch.setArnSnsOpenSearchBPOP(test);
        arnSnsOpenSearch.setArnSnsOpenSearchDALE(test);

        SnsSelectorUtil snsSelectorUtil = new SnsSelectorUtil(arnSnsOpenSearch);

        String result = snsSelectorUtil.selectSNS(BankIdEnum.BANCO_AV_VILLAS.getValue());

        Assertions.assertEquals(test, result);

    }

}
