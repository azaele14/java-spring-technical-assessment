package com.ventadirecta.pruebatecnica.service.impl;

import com.ventadirecta.pruebatecnica.dto.ProductDTO;

import java.util.List;

public interface ProductServiceImpl {

    List<ProductDTO> getAllProducts();
    ProductDTO createProduct(ProductDTO product);
    ProductDTO updateProduct(Long id, ProductDTO product);
    void deleteProduct(Long id);

}
