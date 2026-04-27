package org.test.client;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.test.client.exception.ProductClientException;
import org.test.client.exception.ProductNotFoundException;
import org.test.client.exception.ProductServiceUnavailableException;
import org.test.dto.request.ProductDTO;
import org.test.dto.response.JsonApiData;
import org.test.dto.response.JsonApiResponse;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
class ProductClientTest {

    @Test
    void shouldReturnProduct() {

        RestTemplate restTemplate = mock(RestTemplate.class);

        ProductClient client = new ProductClient(restTemplate);

        ProductDTO product = new ProductDTO();
        product.setId(1L);

        JsonApiData<ProductDTO> data = new JsonApiData<>();
        data.setAttributes(product);

        JsonApiResponse<ProductDTO> response = new JsonApiResponse<>();
        response.setData(data);

        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(ResponseEntity.ok(response));

        client.getProduct(1L);

        verify(restTemplate).exchange(
                anyString(),
                any(HttpMethod.class),
                any(),
                any(ParameterizedTypeReference.class)
        );
    }
    @Test
    void shouldThrowWhenBodyIsNull() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        ProductClient client = new ProductClient(restTemplate);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(ResponseEntity.ok(null));

        assertThrows(ProductNotFoundException.class,
                () -> client.getProduct(1L));
    }

    @Test
    void shouldThrowWhenDataIsNull() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        ProductClient client = new ProductClient(restTemplate);
        JsonApiResponse<ProductDTO> response = new JsonApiResponse<>();
        response.setData(null);

        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(ResponseEntity.ok(response));

        assertThrows(ProductNotFoundException.class,
                () -> client.getProduct(1L));
    }

    @Test
    void shouldThrowProductNotFound() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        ProductClient client = new ProductClient(restTemplate);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(),
                any(ParameterizedTypeReference.class)
        )).thenThrow(HttpClientErrorException.NotFound.class);

        assertThrows(ProductNotFoundException.class,
                () -> client.getProduct(1L));
    }

    @Test
    void shouldThrowProductClientException() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        ProductClient client = new ProductClient(restTemplate);
        HttpStatusCodeException ex =
                mock(HttpStatusCodeException.class);

        when(ex.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);

        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(),
                any(ParameterizedTypeReference.class)
        )).thenThrow(ex);

        assertThrows(ProductClientException.class,
                () -> client.getProduct(1L));
    }

    @Test
    void shouldThrowServiceUnavailableWhenConnectionFails() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        ProductClient client = new ProductClient(restTemplate);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(),
                any(ParameterizedTypeReference.class)
        )).thenThrow(new ResourceAccessException("timeout"));

        assertThrows(ProductServiceUnavailableException.class,
                () -> client.getProduct(1L));
    }
}
