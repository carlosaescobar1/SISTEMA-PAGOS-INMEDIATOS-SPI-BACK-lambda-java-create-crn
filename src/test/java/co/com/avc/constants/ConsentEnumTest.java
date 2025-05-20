package co.com.avc.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsentEnumTest {

    @Test
    void testGetValue() {
        assertEquals("S", ConsentEnum.S.getValue());
        assertEquals("N", ConsentEnum.N.getValue());
    }

    @Test
    void testConsentEnum() {
        assertEquals(2, ConsentEnum.values().length);
        assertEquals(ConsentEnum.S, ConsentEnum.valueOf("S"));
        assertEquals(ConsentEnum.N, ConsentEnum.valueOf("N"));
    }

}