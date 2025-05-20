package co.com.avc.models.parameter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParamDynamoTest {

    private ParamDynamo paramDynamo = new ParamDynamo();

    @Test
    void ParamDynamo() {

        String test = "test";

        paramDynamo.setEndPoint(test);
        paramDynamo.setNameTable(test);

        Assertions.assertEquals(test, paramDynamo.getEndPoint());
        Assertions.assertEquals(test, paramDynamo.getNameTable());

    }

}
