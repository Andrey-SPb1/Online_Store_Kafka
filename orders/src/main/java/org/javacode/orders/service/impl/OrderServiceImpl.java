package org.javacode.orders.service.impl;

import lombok.RequiredArgsConstructor;
import org.javacode.common.model.OrderDto;
import org.javacode.orders.model.Order;
import org.javacode.common.model.Status;
import org.javacode.orders.kafka.OrderProducer;
import org.javacode.orders.repository.OrderRepository;
import org.javacode.orders.service.OrderService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    @Override
    public Order createOrder(Order order) {
        OrderDto orderDto = new OrderDto(
                order.getId(),
                order.getPrice(),
                order.getStatus());
        Order saved = orderRepository.save(order);
        orderRepository.flush();
        orderProducer.send(orderDto);
        return saved;
    }

    @Override
    public void updateStatusById(Long id, Status status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Order with id %d not found", id)));
        order.setStatus(status);
        orderRepository.save(order);
    }
}
