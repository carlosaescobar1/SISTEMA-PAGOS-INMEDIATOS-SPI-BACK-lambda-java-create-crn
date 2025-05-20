package co.com.avc.models.parameter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class VaultServicesTimeOutTest {

    private VaultServicesTimeOut vaultServicesTimeOut = new VaultServicesTimeOut();

    @Test
    void vaultParamTest() {

        vaultServicesTimeOut.setRedEnrollmentTimeOut(1500);
        Assertions.assertEquals(1500, vaultServicesTimeOut.getRedEnrollmentTimeOut());

    }

}
