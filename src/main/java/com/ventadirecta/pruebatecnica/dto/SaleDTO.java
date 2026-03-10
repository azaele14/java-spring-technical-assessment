package com.ventadirecta.pruebatecnica.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleDTO {
    private Long id;
    private LocalDate date;
    private String status;

    private Long storeId;

    private List<SaleDetailDTO> details;

    private Double total;
}
