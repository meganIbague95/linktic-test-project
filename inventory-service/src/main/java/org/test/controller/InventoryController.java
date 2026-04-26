package org.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.test.dto.request.PurchaseRequestDTO;
import org.test.dto.response.InventoryResponseDTO;
import org.test.dto.response.PurchaseResponseDTO;
import org.test.service.InventoryService;

@Tag(name = "Inventory", description = "Inventory management APIs")
@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    @Operation(summary = "Get inventory by product ID")
    @GetMapping("/{productId}")
    public InventoryResponseDTO getInventory(@PathVariable Long productId) {
        return service.getInventory(productId);
    }

    @Operation(summary = "Update product stock")
    @PutMapping("/{productId}")
    public ResponseEntity<InventoryResponseDTO> updateStock(@PathVariable Long productId, @RequestParam Integer quantity) {
        InventoryResponseDTO response = service.updateStock(productId, quantity);
        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Execute purchase flow")
    @PostMapping("/purchase")
    public PurchaseResponseDTO purchase(@RequestBody PurchaseRequestDTO dto) {
        return service.purchase(dto.getProductId(), dto.getQuantity());
    }
}
