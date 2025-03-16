package com.tabcorp.saleReport.model;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String productCode;
    private Double cost;
    private String status;
}
