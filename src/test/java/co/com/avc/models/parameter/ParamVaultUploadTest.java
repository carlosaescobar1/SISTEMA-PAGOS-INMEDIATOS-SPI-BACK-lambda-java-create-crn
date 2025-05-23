package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParamVaultUploadTest {

    @Test
    void testGettersAndSetters() {
        ParamVaultUpload paramVaultUpload = new ParamVaultUpload();
        paramVaultUpload.setVaultName("Vault");
        paramVaultUpload.setArnSnsVaultEnrollment("arn:aws:sns:us-east-1:123456789012:TestTopic");
        paramVaultUpload.setUrlEnrollmentVault("https:0.0.0.0");

        assertEquals("Vault", paramVaultUpload.getVaultName());
        assertEquals("arn:aws:sns:us-east-1:123456789012:TestTopic", paramVaultUpload.getArnSnsVaultEnrollment());
        assertEquals("https:0.0.0.0", paramVaultUpload.getUrlEnrollmentVault());
    }
}