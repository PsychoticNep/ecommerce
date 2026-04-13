package com.ecommerce.productapi.service;

import com.ecommerce.productapi.dto.ProductRequest;
import com.ecommerce.productapi.dto.ProductResponse;
import com.ecommerce.productapi.model.Product;
import com.ecommerce.productapi.model.ProductStatus;
import com.ecommerce.productapi.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest request) {
        Product product = mapToEntity(request);
        if (product.getStatus() == null) {
            product.setStatus(ProductStatus.ACTIVE);
        }
        return mapToResponse(productRepository.save(product));
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> searchProducts(
            String name, String category,
            BigDecimal minPrice, BigDecimal maxPrice,
            ProductStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.searchProducts(name, category, minPrice, maxPrice, status, pageable)
                .map(this::mapToResponse);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        updateEntityFromRequest(product, request);
        return mapToResponse(productRepository.save(product));
    }

    public ProductResponse patchProductStock(Long id, int quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        product.setStockQuantity(quantity);
        if (quantity == 0) product.setStatus(ProductStatus.OUT_OF_STOCK);
        else if (product.getStatus() == ProductStatus.OUT_OF_STOCK) product.setStatus(ProductStatus.ACTIVE);
        return mapToResponse(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getLowStockProducts(int threshold) {
        return productRepository
                .findByStockQuantityLessThanAndStatus(threshold, ProductStatus.ACTIVE)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<String> getAllCategories() {
        return productRepository.findDistinctCategoryBy();
    }

    private Product mapToEntity(ProductRequest req) {
        return Product.builder()
                .name(req.getName())
                .description(req.getDescription())
                .price(req.getPrice())
                .category(req.getCategory())
                .stockQuantity(req.getStockQuantity())
                .imageUrl(req.getImageUrl())
                .status(req.getStatus() != null ? req.getStatus() : ProductStatus.ACTIVE)
                .build();
    }

    private void updateEntityFromRequest(Product product, ProductRequest req) {
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setCategory(req.getCategory());
        product.setStockQuantity(req.getStockQuantity());
        product.setImageUrl(req.getImageUrl());
        if (req.getStatus() != null) product.setStatus(req.getStatus());
    }

    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .stockQuantity(product.getStockQuantity())
                .imageUrl(product.getImageUrl())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
