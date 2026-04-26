package org.test.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Purchase request payload")
public class PurchaseRequestDTO {
    @Schema(example = "1")
    private Long productId;

    @Schema(example = "1")
    private Integer quantity;
}
