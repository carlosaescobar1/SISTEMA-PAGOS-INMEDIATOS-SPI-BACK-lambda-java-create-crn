package co.com.avc.models;

import co.com.ath.opensearch.logs.entity.index_batch.OSIndexBatch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageDtoBatchTest {
    @Test
    void testConstructor() {
        // Arrange
        MessageDtoBatch messageDtoBatch = new MessageDtoBatch();
        String idOpensearch = "12345";
        OSIndexBatch osIndexBatch = new OSIndexBatch();

        // Act
        messageDtoBatch.setIdOpensearch(idOpensearch);
        messageDtoBatch.setOsIndexBatch(osIndexBatch);

        // Assert
        assertEquals(idOpensearch, messageDtoBatch.getIdOpensearch());
        assertEquals(osIndexBatch, messageDtoBatch.getOsIndexBatch());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        MessageDtoBatch messageDtoBatch = new MessageDtoBatch();
        String idOpensearch = "12345";
        OSIndexBatch osIndexBatch = new OSIndexBatch();

        // Act
        messageDtoBatch.setIdOpensearch(idOpensearch);
        messageDtoBatch.setOsIndexBatch(osIndexBatch);

        // Assert
        assertEquals(idOpensearch, messageDtoBatch.getIdOpensearch());
        assertEquals(osIndexBatch, messageDtoBatch.getOsIndexBatch());
    }
}