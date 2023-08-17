package com.gerardmccann.customer;

import com.gerardmccann.customer.entity.Customer;
import com.gerardmccann.customer.repository.CustomerRepository;
import com.gerardmccann.customer.service.CustomerJPADataAccessService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    @Mock
    private CustomerRepository customerRepository;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }
    @Test
    void selectAllCustomers() {
        // When
        underTest.selectAllCustomers();
        // Then
        verify(customerRepository)
                .findAll();
    }

    @Test
    void selectCustomerById() {

        // Given
        int id = 1;
        // When
        underTest.selectCustomerById(id);
        // Then
        verify(customerRepository)
                .findById(id);
    }

    @Test
    void deleteCustomerById() {

        // Given
        int id = 1;
        // When
        underTest.deleteCustomerById(id);
        // Then
        verify(customerRepository)
                .deleteById(id);
    }

    @Test
    void insertCustomer() {

        // Given
        Customer customer = new Customer(
                1,
                "John",
                "john@gmail.com",
                20
        );

        // When
        underTest.insertCustomer(customer);
        // Then
        verify(customerRepository)
                .save(customer);
    }

    @Test
    void updateCustomer() {

        // Given
        Customer customer = new Customer(
                1,
                "John",
                "john@gmail.com",
                20
        );
        // When
        underTest.updateCustomer(customer);
        // Then
        verify(customerRepository)
                .save(customer);
    }

    @Test
    void existsCustomerWithEmail() {

        // Given
        String email = "John@gmail.com";
        // When
        underTest.existsCustomerWithEmail(email);
        // Then
        verify(customerRepository)
                .existsCustomerByEmail(email);
    }

    @Test
    void existsCustomerWithId() {

        // Given
        int id = 1;
        // When
        underTest.existsCustomerWithId(id);
        // Then
        verify(customerRepository)
                .existsCustomerById(id);
    }
}