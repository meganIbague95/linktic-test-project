package org.test.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.test.dto.request.ProductDTO;

@Data
@AllArgsConstructor
public class InventoryResponseDTO {
    private ProductDTO product;
    private Integer quantity;
}
