package br.edu.udesc.ecommerce.catalog.domain.strategy;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data @Builder
public class ProductSearchCriteria {
    private String name;
    private UUID categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private UUID sellerId;
    private int page;
    private int size;
}
