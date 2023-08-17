package com.gerardmccann.customer.dao;

import com.gerardmccann.customer.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {

    List<Customer> selectAllCustomers();

    Optional<Customer> selectCustomerById(Integer id);
    void insertCustomer(Customer customer);
    void updateCustomer(Customer update);
    boolean existsCustomerWithEmail(String email);
    boolean existsCustomerWithId(Integer id);
    void deleteCustomerById(Integer id);

}
