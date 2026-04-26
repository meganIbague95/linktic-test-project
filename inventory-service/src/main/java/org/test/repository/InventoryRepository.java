package org.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.test.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
