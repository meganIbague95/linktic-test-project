package org.test.client.exception;

public class ProductServiceUnavailableException extends ProductClientException {

    public ProductServiceUnavailableException() {
        super("Product service is unavailable", 503);
    }
}
