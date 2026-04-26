package org.test.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.test.dto.request.ProductDTO;
import org.test.dto.response.JsonApiResponse;
import org.test.client.exception.ProductClientException;
import org.test.client.exception.ProductNotFoundException;
import org.test.client.exception.ProductServiceUnavailableException;

@Component
@RequiredArgsConstructor
public class ProductClient {

    private final RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public ProductDTO getProduct(Long productId) {

        try {

            ResponseEntity<JsonApiResponse<ProductDTO>> response =
                    restTemplate.exchange(
                            productServiceUrl + productId,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<JsonApiResponse<ProductDTO>>() {}
                    );
            var body = response.getBody();
            if (body == null || body.getData() == null) {
                throw new ProductServiceUnavailableException();
            }

            return body.getData().getAttributes();

        } catch (HttpClientErrorException.NotFound ex) {
            throw new ProductNotFoundException(productId);

        } catch (HttpStatusCodeException ex) {
            throw new ProductClientException(
                    "Error calling product-service: " + ex.getStatusCode(),
                    ex.getStatusCode().value()
            );

        } catch (ResourceAccessException ex) {
            // timeout / conexión caída
            throw new ProductServiceUnavailableException();
        }
    }
}
