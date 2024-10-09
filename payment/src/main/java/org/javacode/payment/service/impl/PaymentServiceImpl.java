package org.javacode.payment.service.impl;

import lombok.RequiredArgsConstructor;
import org.javacode.common.model.OrderDto;
import org.javacode.payment.model.Payment;
import org.javacode.payment.kafka.PaymentProducer;
import org.javacode.payment.repository.PaymentRepository;
import org.javacode.payment.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentProducer paymentProducer;

    @Override
    public Payment createPayment(Payment payment, OrderDto order) {
        payment.setOrderId(order.id());
        paymentProducer.send(order);
        return paymentRepository.save(payment);
    }
}
