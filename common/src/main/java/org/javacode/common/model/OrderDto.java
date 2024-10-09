package org.javacode.common.model;

public record OrderDto(
        Long id,
        Double price,
        Status status
) {
}
