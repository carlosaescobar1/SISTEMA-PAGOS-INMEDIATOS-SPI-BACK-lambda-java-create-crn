package co.com.avc.models.parameter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParamActiveVaultTest {

    @org.junit.jupiter.api.Test
    void testGettersAndSetters() {
        ParamActiveVault paramActiveVault = new ParamActiveVault();
        ParamVaultRec vaultRec = new ParamVaultRec();
        List<ParamVaultUpload> vaultsUpload = new ArrayList<>();

        paramActiveVault.setVaultRec(vaultRec);
        paramActiveVault.setVaultsUpload(vaultsUpload);

        assertEquals(vaultRec, paramActiveVault.getVaultRec());
        assertEquals(vaultsUpload, paramActiveVault.getVaultsUpload());
    }

}