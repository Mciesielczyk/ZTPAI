package com.example.demo;

import com.example.demo.service.OrderService;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private final OrderService orderService = new OrderService();

    @Test
    void shouldReturnCorrectSumForValidList() {
        List<Double> prices = Arrays.asList(10.0, 20.0, 30.0);
        Double expectedSum = 60.0;

        Double actualSum = orderService.calculateTotal(prices);
        assertEquals(expectedSum, actualSum);
    }

    /*   @Test
       void shouldReturnCorrectSumForValidList2(){
           List<Double> prices = Arrays.asList(10.0, 20.0, 30.0);
           Double expectedSum = 60.0;
           Double actualSum = orderService.calculateTotal_2(prices);
           assertEquals(expectedSum, actualSum);
       }

       @Test
       void shouldReturnZeroForEmptyList2() {
           List<Double> prices = Collections.emptyList();
           Double actualSum = orderService.calculateTotal_2(prices);
           assertEquals(0.0, actualSum);
       }
   */
    @Test
    void shouldReturnZeroForEmptyList() {
        List<Double> prices = Collections.emptyList();
        Double actualSum = orderService.calculateTotal(prices);
        assertEquals(0.0, actualSum);
    }

    @Test
    void shouldThrowExceptionWhenListIsNull() {
        List<Double> prices = null;
        assertThrows(IllegalArgumentException.class, () -> {
            orderService.calculateTotal(prices);
        });


    }
}
