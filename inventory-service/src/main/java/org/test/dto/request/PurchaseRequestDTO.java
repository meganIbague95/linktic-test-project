package org.test.dto.request;

import lombok.Data;

@Data
public class PurchaseRequestDTO {
    private Long productId;
    private Integer quantity;
}
