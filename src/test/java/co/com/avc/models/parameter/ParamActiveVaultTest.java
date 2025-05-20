package co.com.avc.models.parameter;

import java.util.ArrayList;
import java.util.List;

class ParamActiveVaultTest {

    @org.junit.jupiter.api.Test
    void testGettersAndSetters() {
        ParamActiveVault paramActiveVault = new ParamActiveVault();
        ParamVaultRec vaultRec = new ParamVaultRec();
        List<ParamVaultUpload> vaultsUpload = new ArrayList<>();
        String vaultUploadMas = "test";

        paramActiveVault.setVaultRec(vaultRec);
        paramActiveVault.setVaultsUpload(vaultsUpload);
        paramActiveVault.setVaultUploadMas(vaultUploadMas);

        assertEquals(vaultRec, paramActiveVault.getVaultRec());
        assertEquals(vaultsUpload, paramActiveVault.getVaultsUpload());
        assertEquals(vaultUploadMas, paramActiveVault.getVaultUploadMas());
    }

}