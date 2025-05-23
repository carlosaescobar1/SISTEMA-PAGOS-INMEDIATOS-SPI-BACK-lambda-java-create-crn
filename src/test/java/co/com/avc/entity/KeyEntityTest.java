package co.com.avc.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KeyEntityTest {

    @Test
    void testKeyEntity() {

        KeyEntity keyEntity = new KeyEntity();

        String keyType = "keyType";
        String keyId = "keyId";
        String result = "KeyEntity(keyType=keyType, keyId=keyId)";

        keyEntity.setKeyType(keyType);
        keyEntity.setKeyId(keyId);

        assertEquals(keyType, keyEntity.getKeyType());
        assertEquals(keyId, keyEntity.getKeyId());
        assertEquals(result, keyEntity.toString());
    }
}