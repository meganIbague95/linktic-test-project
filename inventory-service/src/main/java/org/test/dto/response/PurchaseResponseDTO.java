package org.test.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.test.dto.request.ProductDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponseDTO {
    private ProductDTO product;
    private Integer quantityPurchased;
    private Integer remainingStock;
}
