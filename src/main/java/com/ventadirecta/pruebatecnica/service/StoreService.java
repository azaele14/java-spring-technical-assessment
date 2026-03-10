package com.ventadirecta.pruebatecnica.service;

import com.ventadirecta.pruebatecnica.dto.StoreDTO;
import com.ventadirecta.pruebatecnica.mapper.Mapper;
import com.ventadirecta.pruebatecnica.model.Store;
import com.ventadirecta.pruebatecnica.repository.StoreRepository;
import com.ventadirecta.pruebatecnica.service.impl.StoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService implements StoreServiceImpl {

    @Autowired
    private StoreRepository storeRepository;

    @Override
    public List<StoreDTO> getAllStores() {
        return storeRepository.findAll()
                .stream()
                .map(Mapper:: mapStoreToStoreDTO)
                .toList();
    }

    @Override
    public StoreDTO createStore(StoreDTO storeDto) {
        Store store = Store.builder()
                .name(storeDto.getName())
                .address(storeDto.getAddress())
                .build();
        return Mapper.mapStoreToStoreDTO(storeRepository.save(store));
    }

    @Override
    public StoreDTO updateStore(Long id, StoreDTO storeDto) {
            Store storeCurrent = storeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Store not found"));

            storeCurrent.setName(storeDto.getName());
            storeCurrent.setAddress(storeDto.getAddress());

        return Mapper.mapStoreToStoreDTO(storeRepository.save(storeCurrent));
    }

    @Override
    public void deleteStore(Long id) {
        if(!storeRepository.existsById(id))
            throw new RuntimeException("Store not found");

        storeRepository.deleteById(id);
    }
}
