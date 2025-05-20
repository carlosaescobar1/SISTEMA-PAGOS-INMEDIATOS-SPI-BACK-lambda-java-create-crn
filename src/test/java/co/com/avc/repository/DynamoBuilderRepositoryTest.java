package co.com.avc.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class DynamoBuilderRepositoryTest {

    private DynamoBuilderRepository dynamoBuilderRepository;

    @BeforeEach
    void setUp() {
        dynamoBuilderRepository = new DynamoBuilderRepository("us-east-1");
    }

    @Test
    void getClient_WhenExceptionOccurs_ShouldReturnNull() {
        try (MockedStatic<DynamoDbClient> mockedDynamoDbClient = Mockito.mockStatic(DynamoDbClient.class)) {
            // Mock DynamoDbClient para lanzar excepción
            mockedDynamoDbClient.when(DynamoDbClient::builder).thenThrow(new RuntimeException("Test exception"));

            // Act
            DynamoDbEnhancedClient result = dynamoBuilderRepository.getClient();

            // Assert
            assertNull(result);
        }
    }
}