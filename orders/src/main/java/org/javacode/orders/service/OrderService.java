package org.javacode.orders.service;

import org.javacode.orders.model.Order;
import org.javacode.common.model.Status;

public interface OrderService {

    Order createOrder(Order order);

    void updateStatusById(Long id, Status status);

}
