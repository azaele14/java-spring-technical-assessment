package com.ventadirecta.pruebatecnica.service;

import com.ventadirecta.pruebatecnica.dto.ProductDTO;
import com.ventadirecta.pruebatecnica.exceptions.NotFoundException;
import com.ventadirecta.pruebatecnica.mapper.Mapper;
import com.ventadirecta.pruebatecnica.model.Product;
import com.ventadirecta.pruebatecnica.repository.ProductRepository;
import com.ventadirecta.pruebatecnica.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ProductServiceImpl {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(Mapper::mapProductToProductDTO).toList();
    }

    @Override
    public ProductDTO createProduct(ProductDTO product) {
        var prod = Product.builder()
                .name(product.getName())
                .price(product.getPrice())
                .category(product.getCategory())
                .quantity(product.getQuantity())
                .build();
        return Mapper.mapProductToProductDTO(productRepository.save(prod));
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO product) {
        Product productCurrent = productRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Product not found"));

        productCurrent.setName(product.getName());
        productCurrent.setPrice(product.getPrice());
        productCurrent.setCategory(product.getCategory());
        productCurrent.setQuantity(product.getQuantity());

        return Mapper.mapProductToProductDTO(productRepository.save(productCurrent));

    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }

}
