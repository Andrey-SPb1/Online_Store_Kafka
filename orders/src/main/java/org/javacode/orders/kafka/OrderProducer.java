package org.javacode.orders.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.javacode.common.model.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private static final String TOPIC = "new_orders";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(OrderProducer.class);

    public void send(OrderDto order) {
        logger.info("New order: {}", order);
        try {
            String message = objectMapper.writeValueAsString(order);
            kafkaTemplate.send(TOPIC, message);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
    }

}
