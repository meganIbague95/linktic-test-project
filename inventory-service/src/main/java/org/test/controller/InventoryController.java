package org.test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.test.dto.request.PurchaseRequestDTO;
import org.test.dto.response.InventoryResponseDTO;
import org.test.dto.response.PurchaseResponseDTO;
import org.test.service.InventoryService;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    @GetMapping("/{productId}")
    public InventoryResponseDTO getInventory(@PathVariable Long productId) {
        return service.getInventory(productId);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<InventoryResponseDTO> updateStock(@PathVariable Long productId, @RequestParam Integer quantity) {
        InventoryResponseDTO response = service.updateStock(productId, quantity);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/purchase")
    public PurchaseResponseDTO purchase(@RequestBody PurchaseRequestDTO dto) {
        return service.purchase(dto.getProductId(), dto.getQuantity());
    }
}
