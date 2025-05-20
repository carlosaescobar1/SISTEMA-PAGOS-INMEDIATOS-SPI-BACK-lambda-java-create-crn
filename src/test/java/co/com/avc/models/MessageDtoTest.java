package co.com.avc.models;

import org.junit.jupiter.api.Test;

class MessageDtoTest {

    @Test
    void testSettersAndGetters() {
        // Arrange
        MessageDto messageDto = new MessageDto();
        MessageDtoBatch messageDtoBatch = new MessageDtoBatch();
        MessageDtoDynamo messageDtoDynamo = new MessageDtoDynamo();

        // Act
        messageDto.setMessageDtoBatch(messageDtoBatch);
        messageDto.setMessageDtoDynamo(messageDtoDynamo);

        // Assert
        assertEquals(messageDtoBatch, messageDto.getMessageDtoBatch());
        assertEquals(messageDtoDynamo, messageDto.getMessageDtoDynamo());
    }
}