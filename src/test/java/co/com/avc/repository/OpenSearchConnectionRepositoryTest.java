package co.com.avc.repository;

import co.com.avc.models.SecretManagerDto;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.rest_client.RestClientTransport;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenSearchConnectionRepositoryTest {

    private OpenSearchConnectionRepository openSearchConnectionRepository;
    private SecretManagerDto secretManagerDto;

    @BeforeEach
    void setUp() {
        // Configurar SecretManagerDto con valores de prueba
        secretManagerDto = new SecretManagerDto();
        secretManagerDto.setUsername("testUser");
        secretManagerDto.setKeyword("testPassword");
        secretManagerDto.setHostname("localhost");
        secretManagerDto.setPort(9200);
        secretManagerDto.setSchema("https");

        openSearchConnectionRepository = new OpenSearchConnectionRepository(secretManagerDto);
    }

    @Test
    void createHttpRequest_ShouldReturnOpenSearchClient() {
        try (
                MockedStatic<RestClient> mockedRestClient = Mockito.mockStatic(RestClient.class);
                MockedConstruction<BasicCredentialsProvider> mockedCredentialsProvider = Mockito.mockConstruction(
                        BasicCredentialsProvider.class, (mock, context) -> {
                            // No necesitamos hacer nada aquí, solo permitir la creación del objeto
                        });
                MockedConstruction<RestClientTransport> mockedTransport = Mockito.mockConstruction(
                        RestClientTransport.class, (mock, context) -> {
                            // No necesitamos hacer nada aquí, solo permitir la creación del objeto
                        });
                MockedConstruction<OpenSearchClient> mockedClient = Mockito.mockConstruction(
                        OpenSearchClient.class, (mock, context) -> {
                            // No necesitamos hacer nada aquí, solo permitir la creación del objeto
                        })
        ) {
            // Configurar mocks
            RestClientBuilder mockBuilder = mock(RestClientBuilder.class);
            RestClient mockRestClient = mock(RestClient.class);

            mockedRestClient.when(() -> RestClient.builder(any(HttpHost.class)))
                    .thenReturn(mockBuilder);

            when(mockBuilder.setHttpClientConfigCallback(any()))
                    .thenReturn(mockBuilder);

            when(mockBuilder.build())
                    .thenReturn(mockRestClient);

            // Act
            OpenSearchClient result = openSearchConnectionRepository.createHttpRequest();

            // Assert
            assertNotNull(result);
        }
    }
}