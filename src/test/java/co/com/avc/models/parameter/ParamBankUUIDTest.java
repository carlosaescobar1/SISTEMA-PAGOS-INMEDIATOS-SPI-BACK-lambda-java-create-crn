package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;

class ParamBankUUIDTest {
    // Test the getters and setters of ParamBankUUID class
    @Test
    void testParamBankUUID() {
        // Create an instance of ParamBankUUID
        ParamBankUUID paramBankUUID = new ParamBankUUID();

        // Set values for the fields
        paramBankUUID.setBavv("BAVV");
        paramBankUUID.setBbog("BBOG");
        paramBankUUID.setBocc("BOCC");
        paramBankUUID.setBpop("BPOP");
        paramBankUUID.setDale("DALE");

        // Assert that the values are set correctly
        assertEquals("BAVV", paramBankUUID.getBavv());
        assertEquals("BBOG", paramBankUUID.getBbog());
        assertEquals("BOCC", paramBankUUID.getBocc());
        assertEquals("BPOP", paramBankUUID.getBpop());
        assertEquals("DALE", paramBankUUID.getDale());
    }
}