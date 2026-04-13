package com.ecommerce.productapi.dto;

import com.ecommerce.productapi.model.ProductStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @NotBlank(message = "Category is required")
    private String category;

    @Min(0)
    private int stockQuantity;

    private String imageUrl;

    private ProductStatus status;
}
