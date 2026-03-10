package com.ventadirecta.pruebatecnica.service.impl;

import com.ventadirecta.pruebatecnica.dto.StoreDTO;

import java.util.List;

public interface StoreServiceImpl {

    List<StoreDTO> getAllStores();
    StoreDTO createStore(StoreDTO store);
    StoreDTO updateStore(Long id, StoreDTO store);
    void deleteStore(Long id);

}
