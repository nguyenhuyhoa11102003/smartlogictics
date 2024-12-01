package com.tdtu.logistics_inventory_service.dto.res;

import com.tdtu.logistics_goods_service.model.Category;

public record CategoryResponse(
        Long id,             // ID of the category
        String name,         // Name of the category
        String description   // Description of the category
) {
    // Static method to map the entity to the response DTO
    public static CategoryResponse fromEntity(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}