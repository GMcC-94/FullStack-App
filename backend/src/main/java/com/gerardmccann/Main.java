package com.gerardmccann;

import com.gerardmccann.customer.entity.Customer;
import com.gerardmccann.customer.repository.CustomerRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication(scanBasePackages = {"com.gerardmccann"})
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository ){
        return args -> {
        var faker = new Faker();
        var name = faker.name();

        String firstName = name.firstName();
        String lastName = name.lastName();

        Random random = new Random();

        Customer customer = new Customer(
               firstName + " " + lastName,
                firstName.toLowerCase() + "." + lastName.toLowerCase()
                + "@hotmail.com",
                random.nextInt(16, 99)
        );
        customerRepository.save(customer);
        };
    }
}
