package com.ventadirecta.pruebatecnica.service.impl;

import com.ventadirecta.pruebatecnica.dto.SaleDTO;

import java.util.List;

public interface SaleServiceImpl {

    List<SaleDTO> getSales();
    SaleDTO createSale(SaleDTO sale);
    SaleDTO updateSale(Long id, SaleDTO sale);
    void deleteSale(Long id);

}
