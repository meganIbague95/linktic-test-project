package org.test.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.test.client.ProductClient;
import org.test.dto.request.ProductDTO;
import org.test.dto.response.InventoryResponseDTO;
import org.test.dto.response.PurchaseResponseDTO;
import org.test.exception.InsufficientStockException;
import org.test.exception.ResourceNotFoundException;
import org.test.model.Inventory;
import org.test.repository.InventoryRepository;
import org.test.util.Constants;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository repository;
    private final ProductClient productClient;

    public InventoryResponseDTO getInventory(Long productId) {
        log.info("Fetching inventory for productId={}", productId);
        Inventory inventory = repository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Inventory not found for productId={}", productId);
                    return new ResourceNotFoundException(Constants.INVENTORY_NOT_FOUND);
                });

        ProductDTO product = productClient.getProduct(productId);
        log.info("Inventory fetched successfully for productId={}", productId);
        return new InventoryResponseDTO(product, inventory.getQuantity());
    }

    public InventoryResponseDTO updateStock(Long productId, Integer quantity) {

        log.info("Updating stock: productId={}, quantity={}", productId, quantity);

        Inventory inventory = repository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.INVENTORY_NOT_FOUND));

        inventory.setQuantity(quantity);
        repository.save(inventory);

        ProductDTO product = productClient.getProduct(productId);

        log.info("Stock updated successfully: productId={}, newQuantity={}", productId, quantity);
        return new InventoryResponseDTO(product, inventory.getQuantity());
    }

    public PurchaseResponseDTO purchase(Long productId, Integer quantity) {

        log.info("Starting purchase: productId={}, quantity={}", productId, quantity);

        ProductDTO product = productClient.getProduct(productId);

        Inventory inventory = repository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Inventory not found for purchase, productId={}", productId);
                    return new ResourceNotFoundException(Constants.INVENTORY_NOT_FOUND);
                });

        if (inventory.getQuantity() < quantity) {
            log.warn("Insufficient stock: productId={}, requested={}, available={}",
                    productId, quantity, inventory.getQuantity());
            throw new InsufficientStockException(productId);
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        repository.save(inventory);

        log.info("Purchase completed: productId={}, purchased={}, remaining={}",
                productId, quantity, inventory.getQuantity());

        return PurchaseResponseDTO.builder()
                .product(product)
                .quantityPurchased(quantity)
                .remainingStock(inventory.getQuantity())
                .build();
    }

    public List<InventoryResponseDTO> getAllInventory() {
        return repository.findAll().stream()
                .map(inv -> {
                    ProductDTO product = productClient.getProduct(inv.getProductId());
                    return new InventoryResponseDTO(product, inv.getQuantity());
                })
                .toList();
    }
}
