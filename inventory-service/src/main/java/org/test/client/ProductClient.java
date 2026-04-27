package org.test.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;
import org.test.dto.request.ProductDTO;
import org.test.dto.response.JsonApiResponse;
import org.test.client.exception.ProductClientException;
import org.test.client.exception.ProductNotFoundException;
import org.test.client.exception.ProductServiceUnavailableException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductClient {

    private final RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;

    @Value("${product.service.api-key}")
    private String apiKey;

    @Retryable(
            value = {
                    ResourceAccessException.class,
                    HttpServerErrorException.class
            },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public ProductDTO getProduct(Long productId) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-API-KEY", apiKey);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<JsonApiResponse<ProductDTO>> response =
                    restTemplate.exchange(
                            productServiceUrl + productId,
                            HttpMethod.GET,
                            entity,
                            new ParameterizedTypeReference<JsonApiResponse<ProductDTO>>() {}
                    );
            var body = response.getBody();
            if (body == null || body.getData() == null) {
                throw new ProductNotFoundException(productId);
            }

            return body.getData().getAttributes();

        } catch (HttpClientErrorException.NotFound ex) {
            log.error("Inventory not found");
            throw new ProductNotFoundException(productId);

        } catch (HttpStatusCodeException ex) {
            log.error("Error calling product-service: {}", ex.getStatusCode());
            throw new ProductClientException(
                    "Error calling product-service: " + ex.getStatusCode(),
                    ex.getStatusCode().value()
            );

        } catch (ResourceAccessException ex) {
            throw new ProductServiceUnavailableException();
        }
    }

    @Recover
    public ProductDTO recover(Exception ex, Long productId) {
        throw new ProductServiceUnavailableException();
    }
}
