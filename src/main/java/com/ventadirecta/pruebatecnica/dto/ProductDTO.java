package com.ventadirecta.pruebatecnica.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data transfer object for Product")
public class ProductDTO {

    @Schema(description = "Unique product identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Product name", example = "HP Laptop", required = true)
    private String name;

    @Schema(description = "Product price", example = "899.99", required = true)
    private Double price;

    @Schema(description = "Product category", example = "Electronics")
    private String category;

    @Schema(description = "Available inventory quantity", example = "50.0")
    private Double quantity;

}
