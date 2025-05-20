package co.com.avc.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SecretManagerDtoTest {
    SecretManagerDto secretManagerDto;

    @BeforeEach
    void setUp() {
        secretManagerDto = new SecretManagerDto();
        secretManagerDto.setPort(9200);
        secretManagerDto.setSchema("https");
        secretManagerDto.setKeyword("keyword");
        secretManagerDto.setHostname("localhost");
        secretManagerDto.setUsername("admin");
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(9200, secretManagerDto.getPort());
        assertEquals("https", secretManagerDto.getSchema());
        assertEquals("keyword", secretManagerDto.getKeyword());
        assertEquals("localhost", secretManagerDto.getHostname());
        assertEquals("admin", secretManagerDto.getUsername());
    }

}