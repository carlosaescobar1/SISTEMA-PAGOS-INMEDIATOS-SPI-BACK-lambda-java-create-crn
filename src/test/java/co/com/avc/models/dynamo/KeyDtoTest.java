package co.com.avc.models.dynamo;

import org.junit.jupiter.api.Test;

class KeyDtoTest {

    @Test
    void testKeyDto() {

        String keyType = "keyType";
        String keyId = "keyId";

        KeyDto keyDto = new KeyDto(keyType, keyId);

        keyDto.setKeyType(keyType);
        keyDto.setKeyId(keyId);

        assertEquals(keyType, keyDto.getKeyType());
        assertEquals(keyId, keyDto.getKeyId());

    }

}