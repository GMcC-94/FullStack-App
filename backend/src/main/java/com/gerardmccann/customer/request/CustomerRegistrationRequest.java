package com.gerardmccann.customer.request;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
) {
}
