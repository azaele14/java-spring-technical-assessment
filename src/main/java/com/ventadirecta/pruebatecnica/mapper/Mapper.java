package com.ventadirecta.pruebatecnica.mapper;

import com.ventadirecta.pruebatecnica.dto.ProductDTO;
import com.ventadirecta.pruebatecnica.dto.SaleDetailDTO;
import com.ventadirecta.pruebatecnica.dto.SaleDTO;
import com.ventadirecta.pruebatecnica.dto.StoreDTO;
import com.ventadirecta.pruebatecnica.model.Product;
import com.ventadirecta.pruebatecnica.model.Sale;
import com.ventadirecta.pruebatecnica.model.Store;

import java.util.stream.Collectors;

public class Mapper {

    public static ProductDTO mapProductToProductDTO(Product product) {
        if (product == null) return null;

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .category(product.getCategory())
                .quantity(product.getQuantity())
                .build();
    }

    public static SaleDTO mapSaleToSaleDTO(Sale sale) {
        if (sale == null) return null;

        var detail = sale.getSaleDetails().stream().map(saleDetail ->
                SaleDetailDTO.builder()
                        .id(saleDetail.getProduct().getId())
                        .productId(saleDetail.getProduct().getId())
                        .productName(saleDetail.getProduct().getName())
                        .quantity(saleDetail.getQuantity())
                        .price(saleDetail.getProduct().getPrice())
                        .subtotal(saleDetail.getPrice() * saleDetail.getQuantity())
                        .build()
        ).collect(Collectors.toList());

        var total = detail.stream()
                .map(SaleDetailDTO :: getSubtotal)
                .reduce(0.0, Double::sum);

        return SaleDTO.builder()
                .id(sale.getId())
                .date(sale.getDate())
                .status(sale.getStatus())
                .storeId(sale.getStore().getId())
                .details(detail)
                .total(total)
                .build();
    }

    public static StoreDTO mapStoreToStoreDTO(Store store) {
        if (store == null) return null;

        return StoreDTO.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .build();
    }

}
