package org.javacode.notifications.kafka;

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
public class NotificationsProducer {

    private static final String TOPIC = "notifications_of_success";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(NotificationsProducer.class);

    public void send(OrderDto order) {
        try {
            String message = objectMapper.writeValueAsString(order);
            kafkaTemplate.send(TOPIC, message);
            logger.info("The order has been successfully delivered - {}", order);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
    }

}
