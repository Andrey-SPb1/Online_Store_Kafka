package org.javacode.shipping.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.javacode.common.model.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingConsumer {

    private final ObjectMapper objectMapper;
    private final ShippingProducer shippingProducer;

    private static final Logger logger = LoggerFactory.getLogger(ShippingConsumer.class);


    @KafkaListener(topics = "paid_orders", groupId = "shipping_group")
    public void consumePayedOrder(String message) {
        try {
            OrderDto order = objectMapper.readValue(message, OrderDto.class);
            shippingProducer.send(order);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
    }

}
