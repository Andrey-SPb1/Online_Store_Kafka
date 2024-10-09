package org.javacode.payment.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.javacode.common.model.OrderDto;
import org.javacode.payment.model.Payment;
import org.javacode.payment.service.impl.PaymentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentServiceImpl paymentService;
    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(PaymentConsumer.class);

    @KafkaListener(topics = "new_orders", groupId = "payment_group")
    public void consumePayedOrder(String message) {
        try {
            OrderDto order = objectMapper.readValue(message, OrderDto.class);
            Payment payment = Payment.builder().orderId(order.id()).amount(order.price()).build();

            paymentService.createPayment(payment, order);
            logger.info("Payment created - {}", payment);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
    }

}
