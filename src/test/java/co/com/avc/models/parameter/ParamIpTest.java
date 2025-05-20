package co.com.avc.models.parameter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ParamIpTest {

    @Test
    void testParamIp() {
        ParamIp paramIp = new ParamIp();
        paramIp.setBavv("ipBAVV");
        paramIp.setBbog("ipBBOG");
        paramIp.setBocc("ipBOCC");
        paramIp.setBpop("ipBPOP");
        paramIp.setDale("ipDALE");


        assertEquals("ipBAVV", paramIp.getBavv());
        assertEquals("ipBBOG", paramIp.getBbog());
        assertEquals("ipBOCC", paramIp.getBocc());
        assertEquals("ipBPOP", paramIp.getBpop());
        assertEquals("ipDALE", paramIp.getDale());
        assertNotNull(paramIp.toString());
    }
}