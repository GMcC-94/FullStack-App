package com.gerardmccann.customer.service;

import com.gerardmccann.customer.request.CustomerUpdateRequest;
import com.gerardmccann.customer.dao.CustomerDAO;
import com.gerardmccann.customer.entity.Customer;
import com.gerardmccann.customer.request.CustomerRegistrationRequest;
import com.gerardmccann.exception.RequestValidationException;
import com.gerardmccann.exception.ResourceExistsException;
import com.gerardmccann.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService(@Qualifier("jdbc") CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }


    public List<Customer> getAllCustomers() {
        return customerDAO.selectAllCustomers();
    }

    public Customer getCustomer(Integer id) {
        return customerDAO.selectCustomerById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "customer with id [%s]".formatted(id)
                        ));

    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {

        // Check if email exists
        String email = customerRegistrationRequest.email();
        if (customerDAO.existsCustomerWithEmail(email)) {
            throw new ResourceExistsException("Email already taken");
        }

        // Add customer
        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age()
        );
        customerDAO.insertCustomer(customer);
    }

    public void deleteCustomerById(Integer customerId){
        // If customer does not exist throw exception
        if(!customerDAO.existsCustomerWithId(customerId)){
            throw new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(customerId)
            );
        }

        customerDAO.deleteCustomerById(customerId);
    }

    public void updateCustomer(Integer customerId, CustomerUpdateRequest updateRequest){

        Customer customer = getCustomer(customerId);

        boolean changes = false;

        if(updateRequest.name() != null && !updateRequest.name().equals(customer.getName())){
            customer.setName(updateRequest.name());
            changes = true;
        }


        if(updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())){
            customer.setAge(updateRequest.age());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())){
            if (customerDAO.existsCustomerWithEmail(updateRequest.email())) {
                throw new ResourceExistsException("Email already taken");
            }
            customer.setEmail(updateRequest.email());
            changes = true;
        }

        if(!changes){
            throw new RequestValidationException("No data changes found");
        }

        customerDAO.updateCustomer(customer);
    }
}
