package org.test.event;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.test.model.Inventory;
import org.test.repository.InventoryRepository;

@Component
@RequiredArgsConstructor
public class ProductCreatedListener {

    private final InventoryRepository repository;

    @RabbitListener(queues = "product.created.queue")
    public void handle(Long productId) {
        repository.save(new Inventory(productId, 0));
    }
}
