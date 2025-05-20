package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;

class ParamFlowConfigTest {

    @Test
    void testGettersAndSetters() {
        ParamFlowConfig config = new ParamFlowConfig();

        // Test setting and getting consentFlow
        String consentFlow = "consentFlowValue";
        config.setConsentFlow(consentFlow);
        assertEquals(consentFlow, config.getConsentFlow());

        // Test setting and getting vaultSyncFlow
        String vaultSyncFlow = "vaultSyncFlowValue";
        config.setVaultSyncFlow(vaultSyncFlow);
        assertEquals(vaultSyncFlow, config.getVaultSyncFlow());
    }

}