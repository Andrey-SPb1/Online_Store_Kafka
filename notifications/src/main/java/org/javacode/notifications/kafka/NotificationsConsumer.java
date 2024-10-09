package org.javacode.notifications.kafka;

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
public class NotificationsConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationsProducer notificationsProducer;

    private static final Logger logger = LoggerFactory.getLogger(NotificationsProducer.class);

    @KafkaListener(topics = "sent_orders", groupId = "notification_group")
    public void consumePayedOrder(String message) {
        try {
            OrderDto order = objectMapper.readValue(message, OrderDto.class);
            notificationsProducer.send(order);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
    }
}
