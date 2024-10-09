package org.javacode.common.model;

public record PaymentDto(
        Long id,
        Long orderId,
        Double amount
) {
}
