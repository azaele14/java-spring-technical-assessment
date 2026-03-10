package com.ventadirecta.pruebatecnica.controller;

import com.ventadirecta.pruebatecnica.dto.StoreDTO;
import com.ventadirecta.pruebatecnica.service.impl.StoreServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stores")
@Tag(name = "Stores", description = "API for store management")
public class StoreControllerV1 {

    @Autowired
    private StoreServiceImpl storeService;

    @GetMapping
    @Operation(summary = "Get all stores", description = "Returns a list of all stores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store list retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StoreDTO.class)))
    })
    public ResponseEntity<List<StoreDTO>> getAllStores() {
        List<StoreDTO> stores = storeService.getAllStores();
        return ResponseEntity.ok(stores);
    }

    @PostMapping
    @Operation(summary = "Create a store", description = "Creates a new store in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StoreDTO.class)))
    })
    public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO store) {
        StoreDTO createdStore = storeService.createStore(store);
        return ResponseEntity.ok(createdStore);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a store", description = "Updates an existing store")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StoreDTO.class))),
            @ApiResponse(responseCode = "404", description = "Store not found", content = @Content)
    })
    public ResponseEntity<StoreDTO> updateStore(@PathVariable Long id, @RequestBody StoreDTO store) {
        StoreDTO updatedStore = storeService.updateStore(id, store);
        return ResponseEntity.ok(updatedStore);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a store", description = "Deletes an existing store")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Store deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Store not found", content = @Content)
    })
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
        return ResponseEntity.noContent().build();
    }

}
