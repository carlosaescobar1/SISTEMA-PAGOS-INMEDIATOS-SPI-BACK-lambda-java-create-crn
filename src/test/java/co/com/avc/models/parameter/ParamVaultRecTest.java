package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParamVaultRecTest {

    @Test
    void testGettersAndSetters() {
        ParamVaultRec paramVaultRec = new ParamVaultRec();

        // Set values
        paramVaultRec.setVaultNameRec("testVaultName");

        // Assert values
        assertEquals("testVaultName", paramVaultRec.getVaultNameRec());
    }

}