package de.unikassel.soc.platform.services;

import de.unikassel.soc.platform.domain.Customer;
import de.unikassel.soc.platform.repositories.CustomerRepo;
import de.unikassel.soc.platform.web.model.CustomerDto;
import de.unikassel.soc.platform.web.mappers.CustomerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepo customerRepo;
    private CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepo customerRepo, CustomerMapper customerMapper) {
        this.customerRepo = customerRepo;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerDto getCustomerById(UUID customerId) {
        Customer customer = customerRepo.findById(customerId).get();
        return customerMapper.customerToCustomerDto(customer);
    }

    @Override
    public List<CustomerDto> getCustomerByName(String name) {
        List<Customer> customerList = customerRepo.findByName(name);
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (int i = 0; i < customerList.size(); i++) {
            Customer customer = customerList.get(i);
            CustomerDto customerDto = customerMapper.customerToCustomerDto(customer);
            customerDtoList.add(customerDto);
        }
        return customerDtoList;
        //List<CustomerDto> customerDtoList = Array.asList(customerDtoArray);
    }

/*
    @Override
    public CustomerDto[] getAllCustomer() {
        Iterable<Customer> allCustomers = customerRepo.findAll();

        CustomerDto[] allCustomerDto = new Array;
        for (Customer customer: allCustomers) {
            list.add(customer);
        }
    }*/

    @Override
    public CustomerDto saveNewCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDto);
        customerRepo.save(customer);
        return customerDto;
    }

    @Override
    public void updateCustomer(UUID customerId, CustomerDto customerDto) {
        log.debug("Updating....");
        Customer customer = customerRepo.findById(customerId).get();
        customer.setName(customerDto.getName());
        customerRepo.save(customer);
    }

    @Override
    public void deleteById(UUID customerId) {
        log.debug("Deleting a customer .... ");
        customerRepo.deleteById(customerId);
    }
}
