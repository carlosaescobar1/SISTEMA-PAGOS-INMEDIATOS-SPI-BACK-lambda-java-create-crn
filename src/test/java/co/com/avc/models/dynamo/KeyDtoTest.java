package co.com.avc.models.dynamo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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