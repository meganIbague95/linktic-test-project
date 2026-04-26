package org.test.client.exception;

public class ProductClientException extends RuntimeException {

    private final int status;

    public ProductClientException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
