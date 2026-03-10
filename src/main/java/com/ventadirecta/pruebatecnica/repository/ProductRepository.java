package com.ventadirecta.pruebatecnica.repository;

import com.ventadirecta.pruebatecnica.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //Find product by name
    Optional<Product> findByName(String name);

}
