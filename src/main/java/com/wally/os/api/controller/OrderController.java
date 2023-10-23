package com.wally.os.api.controller;

import com.wally.os.api.entity.Order;
import com.wally.os.api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/book-order")
    public Order bookOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }
}
