package com.test.product_service.service;

import com.test.product_service.dto.request.ProductRequestDTO;
import com.test.product_service.dto.response.ProductResponseDTO;
import com.test.product_service.exception.ProductNotFoundException;
import com.test.product_service.mapper.ProductMapper;
import com.test.product_service.model.Product;
import com.test.product_service.repository.ProductRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final RabbitTemplate rabbitTemplate;

    public ProductService(ProductRepository repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public ProductResponseDTO create(ProductRequestDTO dto) {
        Product product = ProductMapper.toEntity(dto);
        Product saved = repository.save(product);
        rabbitTemplate.convertAndSend("product.exchange", "product.created", saved.getId());
        return ProductMapper.toDTO(saved);
    }

    public ProductResponseDTO getById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ProductMapper.toDTO(product);
    }


    public List<ProductResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }
}
