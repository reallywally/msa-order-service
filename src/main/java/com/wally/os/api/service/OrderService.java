package com.wally.os.api.service;

import com.wally.os.api.common.Payment;
import com.wally.os.api.common.TransactionRequest;
import com.wally.os.api.common.TransactionResponse;
import com.wally.os.api.entity.Order;
import com.wally.os.api.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public TransactionResponse saveOrder(TransactionRequest request) {
        Order order = request.getOrder();

        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());

        Payment paymentResponse = restTemplate.postForObject("http://localhost:9191/payments/doPayment", payment, Payment.class);

        String message = paymentResponse.getPaymentStatus().equals("success") ? "payment processing successful and order placed" : "there is a failure in payment api, order added to cart";
        orderRepository.save(order);
        return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(), message);
    }
}
