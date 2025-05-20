package co.com.avc.models.parameter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParamaterStoreTest {

    private ParameterStoreDto parameterStoreDto = new ParameterStoreDto();
    private ParamDynamo paramDynamo = new ParamDynamo();
    private ArnSnsOpenSearch arnSnsOpenSearch = new ArnSnsOpenSearch();
    private VaultServicesTimeOut vaultServicesTimeOut = new VaultServicesTimeOut();

    @Test
    void paramaterStore() {

        String test = "test";

        paramDynamo.setEndPoint(test);
        paramDynamo.setNameTable(test);

        parameterStoreDto.setParamDynamo(paramDynamo);

        arnSnsOpenSearch.setArnSnsOpenSearchBAVV(test);
        arnSnsOpenSearch.setArnSnsOpenSearchBBOG(test);
        arnSnsOpenSearch.setArnSnsOpenSearchBOCC(test);
        arnSnsOpenSearch.setArnSnsOpenSearchBPOP(test);
        arnSnsOpenSearch.setArnSnsOpenSearchDALE(test);

        parameterStoreDto.setArnSnsOpenSearch(arnSnsOpenSearch);

        parameterStoreDto.setArnSecretOpenSearch(test);

        vaultServicesTimeOut.setRedEnrollmentTimeOut(1500);

        parameterStoreDto.setVaultServicesTimeOut(vaultServicesTimeOut);


        Assertions.assertEquals(paramDynamo, parameterStoreDto.getParamDynamo());
        Assertions.assertEquals(arnSnsOpenSearch, parameterStoreDto.getArnSnsOpenSearch());
        Assertions.assertEquals(vaultServicesTimeOut, parameterStoreDto.getVaultServicesTimeOut());
        Assertions.assertEquals(test, parameterStoreDto.getArnSecretOpenSearch());

    }

}
