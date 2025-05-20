package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;

class ParamVaultRecTest {

    @Test
    void testGettersAndSetters() {
        ParamVaultRec paramVaultRec = new ParamVaultRec();

        // Set values
        paramVaultRec.setVaultNameRec("testVaultName");
        paramVaultRec.setVaultUploadMas("testVaultUpload");

        // Assert values
        assertEquals("testVaultName", paramVaultRec.getVaultNameRec());
        assertEquals("testVaultUpload", paramVaultRec.getVaultUploadMas());
    }

}