package org.javacode.orders.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.javacode.common.model.OrderDto;
import org.javacode.common.model.Status;
import org.javacode.orders.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderConsumer {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);

    @KafkaListener(topics = "paid_orders", groupId = "orders_group")
    public void consumePayedOrder(String message) {
        try {
            OrderDto order = objectMapper.readValue(message, OrderDto.class);
            orderService.updateStatusById(order.id(), Status.PAID);
            logger.info("Paid order: {}", order);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
    }

    @KafkaListener(topics = "shipped_orders", groupId = "orders_group")
    public void consumeShippedOrder(String message) {
        try {
            OrderDto order = objectMapper.readValue(message, OrderDto.class);
            orderService.updateStatusById(order.id(), Status.SHIPPED);
            logger.info("Shipped order: {}", order);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
    }

    @KafkaListener(topics = "notifications_of_success", groupId = "orders_group")
    public void consumeNotifiedOrder(String message) {
        try {
            OrderDto order = objectMapper.readValue(message, OrderDto.class);
            orderService.updateStatusById(order.id(), Status.DELIVERED);
            logger.info("Delivered order: {}", order);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
    }

}
