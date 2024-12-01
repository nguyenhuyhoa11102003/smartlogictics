package com.tdtu.logistics_inventory_service.dto.req;


public record CategoryRequest(
        String name,        // Name of the category
        String description  // Description of the category
) {}