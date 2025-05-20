package co.com.avc.repository;

import co.com.ath.commons.util.ATHException;
import co.com.avc.entity.DynamoSpiEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DynamoRepositoryTest {

    @Mock
    private DynamoDbEnhancedClient client;

    @Mock
    private DynamoDbTable<DynamoSpiEntity> dynamoDbTable;

    private DynamoRepository dynamoRepository;
    private final String tableName = "test-table";

    @BeforeEach
    void setUp() {
        dynamoRepository = new DynamoRepository(client, tableName);
        // Mock de la tabla
        when(client.table(eq(tableName), any(TableSchema.class))).thenReturn(dynamoDbTable);
    }

    @Test
    void load_WhenItemExists_ShouldReturnItem() {
        // Arrange
        String id = "testId";
        String sk = "testSk";
        DynamoSpiEntity expectedEntity = new DynamoSpiEntity();
        expectedEntity.setId(id);
        expectedEntity.setSk(sk);

        when(dynamoDbTable.getItem(any(Key.class))).thenReturn(expectedEntity);

        // Act
        DynamoSpiEntity result = dynamoRepository.load(id, sk);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(sk, result.getSk());
        verify(dynamoDbTable).getItem(any(Key.class));
    }

    @Test
    void load_WhenItemDoesNotExist_ShouldReturnNull() {
        // Arrange
        String id = "nonExistentId";
        String sk = "nonExistentSk";

        when(dynamoDbTable.getItem(any(Key.class))).thenReturn(null);

        // Act
        DynamoSpiEntity result = dynamoRepository.load(id, sk);

        // Assert
        assertNull(result);
        verify(dynamoDbTable).getItem(any(Key.class));
    }

    @Test
    void load_WhenExceptionOccurs_ShouldThrowATHException() {
        // Arrange
        String id = "testId";
        String sk = "testSk";

        when(dynamoDbTable.getItem(any(Key.class))).thenThrow(new RuntimeException("Test exception"));

        // Act & Assert
        assertThrows(ATHException.class, () -> dynamoRepository.load(id, sk));
        verify(dynamoDbTable).getItem(any(Key.class));
    }

    @Test
    void save_WhenSuccessful_ShouldNotThrowExceptions() {
        // Arrange
        DynamoSpiEntity entity = new DynamoSpiEntity();
        entity.setId("testId");
        entity.setSk("testSk");

        doNothing().when(dynamoDbTable).putItem(entity);

        // Act & Assert
        assertDoesNotThrow(() -> dynamoRepository.save(entity));
        verify(dynamoDbTable).putItem(entity);
    }

    @Test
    void save_WhenExceptionOccurs_ShouldThrowATHException() {
        // Arrange
        DynamoSpiEntity entity = new DynamoSpiEntity();
        entity.setId("testId");
        entity.setSk("testSk");

        doThrow(new RuntimeException("Test exception")).when(dynamoDbTable).putItem(entity);

        // Act & Assert
        assertThrows(ATHException.class, () -> dynamoRepository.save(entity));
        verify(dynamoDbTable).putItem(entity);
    }
}