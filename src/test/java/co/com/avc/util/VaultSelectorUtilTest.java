package co.com.avc.util;

import co.com.avc.constants.ConstantsEnum;
import co.com.avc.models.parameter.ParamActiveVault;
import co.com.avc.models.parameter.ParamVaultUpload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class VaultSelectorUtilTest {

    @Mock
    private ParamActiveVault paramActiveVault;

    private VaultSelectorUtil vaultSelectorUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vaultSelectorUtil = new VaultSelectorUtil(paramActiveVault);
    }

    @Test
    void testSelectorVault_WhenVaultFound() {
        // Arrange
        List<ParamVaultUpload> vaults = new ArrayList<>();

        ParamVaultUpload redebanVault = new ParamVaultUpload();
        redebanVault.setVaultName(ConstantsEnum.CORNER.getValue());
        redebanVault.setUrlEnrollmentVault("https://test-redeban.com");
        vaults.add(redebanVault);

        ParamVaultUpload otherVault = new ParamVaultUpload();
        otherVault.setVaultName("OTHER_VAULT");
        otherVault.setUrlEnrollmentVault("https://test-other.com");
        vaults.add(otherVault);

        when(paramActiveVault.getVaultsUpload()).thenReturn(vaults);

        // Act
        ParamVaultUpload result = vaultSelectorUtil.selectorVault();

        // Assert
        assertEquals(ConstantsEnum.CORNER.getValue(), result.getVaultName());
        assertEquals("https://test-redeban.com", result.getUrlEnrollmentVault());
    }

    @Test
    void testSelectorVault_WhenVaultNotFound() {
        // Arrange
        List<ParamVaultUpload> vaults = new ArrayList<>();

        ParamVaultUpload otherVault = new ParamVaultUpload();
        otherVault.setVaultName("OTHER_VAULT");
        otherVault.setUrlEnrollmentVault("https://test-other.com");
        vaults.add(otherVault);

        when(paramActiveVault.getVaultsUpload()).thenReturn(vaults);

        // Act
        ParamVaultUpload result = vaultSelectorUtil.selectorVault();

        // Assert
        assertNull(result);
    }

    @Test
    void testSelectorVault_WithEmptyList() {
        // Arrange
        List<ParamVaultUpload> vaults = new ArrayList<>();
        when(paramActiveVault.getVaultsUpload()).thenReturn(vaults);

        // Act
        ParamVaultUpload result = vaultSelectorUtil.selectorVault();

        // Assert
        assertNull(result);
    }
}