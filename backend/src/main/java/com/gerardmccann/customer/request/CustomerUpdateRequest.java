package com.gerardmccann.customer.request;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
