package org.test.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.test.client.ProductClient;
import org.test.dto.request.ProductDTO;
import org.test.exception.InsufficientStockException;
import org.test.exception.ResourceNotFoundException;
import org.test.model.Inventory;
import org.test.repository.InventoryRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InventoryServiceTest {
    @Mock
    private InventoryRepository repository;

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private InventoryService service;

    public InventoryServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldPurchaseSuccessfully() {

        Long productId = 1L;

        Inventory inventory = new Inventory(productId, 10);
        ProductDTO product = new ProductDTO();
        product.setId(productId);

        when(repository.findById(productId)).thenReturn(Optional.of(inventory));
        when(productClient.getProduct(productId)).thenReturn(product);

        var response = service.purchase(productId, 2);

        assertEquals(8, response.getRemainingStock());
        verify(repository).save(any());
    }

    @Test
    void shouldThrowWhenInsufficientStock() {

        Long productId = 1L;

        Inventory inventory = new Inventory(productId, 1);

        when(repository.findById(productId)).thenReturn(Optional.of(inventory));

        assertThrows(InsufficientStockException.class,
                () -> service.purchase(productId, 5));
    }

    @Test
    void shouldThrowWhenInventoryNotFound() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getInventory(1L));
    }

    @Test
    void shouldUpdateStockSuccessfully() {
        Long productId = 1L;

        Inventory inventory = new Inventory(productId, 5);
        ProductDTO product = new ProductDTO();
        product.setId(productId);

        when(repository.findById(productId)).thenReturn(Optional.of(inventory));
        when(productClient.getProduct(productId)).thenReturn(product);

        var response = service.updateStock(productId, 10);

        assertEquals(10, response.getQuantity());
        verify(repository).save(inventory);
    }

    @Test
    void shouldThrowWhenUpdateStockInventoryNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateStock(1L, 10));
    }


}
