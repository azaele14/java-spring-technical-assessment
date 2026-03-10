package com.ventadirecta.pruebatecnica.service;

import com.ventadirecta.pruebatecnica.dto.SaleDetailDTO;
import com.ventadirecta.pruebatecnica.dto.SaleDTO;
import com.ventadirecta.pruebatecnica.mapper.Mapper;
import com.ventadirecta.pruebatecnica.model.SaleDetail;
import com.ventadirecta.pruebatecnica.model.Product;
import com.ventadirecta.pruebatecnica.model.Store;
import com.ventadirecta.pruebatecnica.model.Sale;
import com.ventadirecta.pruebatecnica.repository.ProductRepository;
import com.ventadirecta.pruebatecnica.repository.StoreRepository;
import com.ventadirecta.pruebatecnica.repository.SaleRepository;
import com.ventadirecta.pruebatecnica.service.impl.SaleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService implements SaleServiceImpl {

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StoreRepository storeRepository;

    @Override
    public List<SaleDTO> getSales() {

        List<Sale> sales = saleRepository.findAll();
        List<SaleDTO> salesDTO = new ArrayList<>();
        SaleDTO saleDto = new SaleDTO();

        for (Sale sale : sales) {
             saleDto = Mapper.mapSaleToSaleDTO(sale);
             salesDTO.add(saleDto);
        }

        return salesDTO;

    }

    @Override
    public SaleDTO createSale(SaleDTO saleDto) {
        if (saleDto == null) throw new RuntimeException("Sale cannot be null");
        if (saleDto.getStoreId() == null) throw new RuntimeException("Store is required");
        if (saleDto.getDetails() == null || saleDto.getDetails().isEmpty())
            throw new RuntimeException("Sale details are required");
        //buscar tienda
        Store store = storeRepository.findById(saleDto.getStoreId()).orElse(null);
        if (store == null) throw new RuntimeException("Store does not exist");

        //crear venta
        Sale sale = new Sale();
        sale.setDate(saleDto.getDate());
        sale.setStatus(saleDto.getStatus());
        sale.setStore(store);
        sale.setTotal(saleDto.getTotal());
        Double totalCalculado = 0.0;

        //lista de detalles de la venta

        List<SaleDetail> saleDetails = new ArrayList<>();

        for(SaleDetailDTO saleDetailDTO : saleDto.getDetails()){
            Product product = productRepository.findById(saleDetailDTO.getProductId()).orElse(null);
            if(product == null) throw new RuntimeException("Product does not exist");

            SaleDetail saleDetail = new SaleDetail();
            saleDetail.setProduct(product);
            saleDetail.setQuantity(saleDetailDTO.getQuantity());
            saleDetail.setPrice(saleDetailDTO.getPrice());
            saleDetail.setSale(sale);
            saleDetails.add(saleDetail);

            saleDetails.add(saleDetail);
            totalCalculado = totalCalculado + (saleDetailDTO.getPrice() * saleDetailDTO.getQuantity());

        }

        sale.setSaleDetails(saleDetails);
        Sale savedSale = saleRepository.save(sale);

        return Mapper.mapSaleToSaleDTO(savedSale);
    }

    @Override
    public SaleDTO updateSale(Long id, SaleDTO saleDto) {
        Sale currentSale = saleRepository.findById(id).orElse(null);
        if (currentSale == null) throw new RuntimeException("Sale not found");

        if (saleDto != null){
            currentSale.setDate(saleDto.getDate());
        }

        if(saleDto.getStatus() != null){
            currentSale.setStatus(saleDto.getStatus());
        }

        if(saleDto.getStoreId() != null){
            currentSale.setStore(storeRepository.findById(saleDto.getStoreId())
                    .orElseThrow(() -> new RuntimeException("Store not found")));

        }

        return Mapper.mapSaleToSaleDTO(currentSale);
    }

    @Override
    public void deleteSale(Long id) {
        if (!saleRepository.existsById(id)) {
                throw new RuntimeException("Sale not found");
            }
            saleRepository.deleteById(id);
    }
}
