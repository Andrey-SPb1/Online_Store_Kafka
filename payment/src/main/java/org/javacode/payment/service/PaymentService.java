package org.javacode.payment.service;

import org.javacode.common.model.OrderDto;
import org.javacode.payment.model.Payment;

public interface PaymentService {

    Payment createPayment(Payment payment, OrderDto order);

}
