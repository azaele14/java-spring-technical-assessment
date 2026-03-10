package com.ventadirecta.pruebatecnica.controller;

import com.ventadirecta.pruebatecnica.dto.SaleDTO;
import com.ventadirecta.pruebatecnica.service.impl.SaleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
@Tag(name = "Sales", description = "API for sales management")
public class SaleControllerV1 {

    @Autowired
    private SaleServiceImpl saleService;

    @GetMapping
    @Operation(summary = "Get sales list", description = "Returns the list of sales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sales list", content = @Content)
    })
    ResponseEntity<List<SaleDTO>> getAllSales() {
        List<SaleDTO> sales = saleService.getSales();
        return ResponseEntity.ok(sales);
    }

    @PostMapping
    @Operation(summary = "Create a sale", description = "Creates a new sale")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale created successfully", content = @Content)
    })
    ResponseEntity<SaleDTO> createSale(SaleDTO sale) {
        SaleDTO createdSale = saleService.createSale(sale);
        return ResponseEntity.ok(createdSale);
    }

    @PutMapping
    @Operation(summary = "Update a sale", description = "Updates an existing sale")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale updated successfully", content = @Content)
    })
    ResponseEntity<SaleDTO> updateSale(Long id, SaleDTO sale) {
        SaleDTO updatedSale = saleService.updateSale(id, sale);
        return ResponseEntity.ok(updatedSale);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a sale", description = "Deletes an existing sale")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale deleted successfully", content = @Content)
    })
    ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.ok().build();
    }

}
