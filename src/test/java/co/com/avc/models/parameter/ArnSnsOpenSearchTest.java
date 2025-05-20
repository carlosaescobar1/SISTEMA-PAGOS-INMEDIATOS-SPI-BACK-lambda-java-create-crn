package co.com.avc.models.parameter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArnSnsOpenSearchTest {

    private ArnSnsOpenSearch arnSnsOpenSearch = new ArnSnsOpenSearch();

    @Test
    void arnSnsOpenSearch() {

        String test = "test";

        arnSnsOpenSearch.setArnSnsOpenSearchBAVV(test);
        arnSnsOpenSearch.setArnSnsOpenSearchBBOG(test);
        arnSnsOpenSearch.setArnSnsOpenSearchBOCC(test);
        arnSnsOpenSearch.setArnSnsOpenSearchBPOP(test);
        arnSnsOpenSearch.setArnSnsOpenSearchDALE(test);

        Assertions.assertEquals(test, arnSnsOpenSearch.getArnSnsOpenSearchBAVV());
        Assertions.assertEquals(test, arnSnsOpenSearch.getArnSnsOpenSearchBBOG());
        Assertions.assertEquals(test, arnSnsOpenSearch.getArnSnsOpenSearchBOCC());
        Assertions.assertEquals(test, arnSnsOpenSearch.getArnSnsOpenSearchBPOP());
        Assertions.assertEquals(test, arnSnsOpenSearch.getArnSnsOpenSearchDALE());

    }

}
