package com.example.demo.service;

import java.util.List;

public class OrderService {


    public Double calculateTotal(List<Double> prices) {
        if (prices == null) {
            throw new IllegalArgumentException("Lista cen nie może być nullem");
        }

        return prices.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public Double calculateTotal_2(List<Double> prices) {
        if (prices == null) {
            throw new IllegalArgumentException("Lista cen nie może być nullem");
        }

        double wyn = 0;
        for(double price : prices) {
            wyn += price;
        }
        return wyn;
    }
}