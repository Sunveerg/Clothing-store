package com.example.customerservice.customersubdomain.businesslayer;




import com.example.customerservice.customersubdomain.datalayer.Address;
import com.example.customerservice.customersubdomain.datalayer.Customer;
import com.example.customerservice.customersubdomain.datalayer.CustomerIdentifier;
import com.example.customerservice.customersubdomain.datalayer.CustomerRepository;
import com.example.customerservice.customersubdomain.datamapperlayer.CustomerRequestMapper;
import com.example.customerservice.customersubdomain.datamapperlayer.CustomerResponseMapper;
import com.example.customerservice.customersubdomain.presentationlayer.CustomerRequestModel;
import com.example.customerservice.customersubdomain.presentationlayer.CustomerResponseModel;
import com.example.customerservice.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerResponseMapper customerResponseMapper;
    private final CustomerRequestMapper customerRequestMapper;


    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerResponseMapper customerResponseMapper,
                               CustomerRequestMapper customerRequestMapper) {
        this.customerRepository = customerRepository;
        this.customerResponseMapper = customerResponseMapper;
        this.customerRequestMapper = customerRequestMapper;

    }

    @Override
    public List<CustomerResponseModel> getCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customerResponseMapper.entityListToResponseModelList(customers);
    }

    @Override
    public CustomerResponseModel getCustomerByCustomerId(String customerId) {
        Customer customer = customerRepository.findByCustomerIdentifier_CustomerId(customerId);

        if (customer == null) {
            throw new NotFoundException("Unknown customerId: " + customerId);
        }
        return customerResponseMapper.entityToResponseModel(customer);
    }

    @Override
    public CustomerResponseModel addCustomer(CustomerRequestModel customerRequestModel) {
        Address address = new Address(customerRequestModel.getStreetAddress(), customerRequestModel.getCity(),
            customerRequestModel.getProvince(), customerRequestModel.getCountry(), customerRequestModel.getPostalCode());

        Customer customer = customerRequestMapper.requestModelToEntity(customerRequestModel, new CustomerIdentifier(), address);

        customer.setAddress(address);
        return customerResponseMapper.entityToResponseModel(customerRepository.save(customer));
    }

    @Override
    public CustomerResponseModel updateCustomer(CustomerRequestModel customerRequestModel, String customerId) {

        Customer existingCustomer = customerRepository.findByCustomerIdentifier_CustomerId(customerId);

        if (existingCustomer == null) {
            throw new NotFoundException("Unknown customerId: " + customerId);
        }
        Address address = new Address(customerRequestModel.getStreetAddress(), customerRequestModel.getCity(),
            customerRequestModel.getProvince(), customerRequestModel.getCountry(), customerRequestModel.getPostalCode());
        Customer updatedCustomer = customerRequestMapper.requestModelToEntity(customerRequestModel,
            existingCustomer.getCustomerIdentifier(), address);
        updatedCustomer.setId(existingCustomer.getId());

        Customer response = customerRepository.save(updatedCustomer);
        return customerResponseMapper.entityToResponseModel(response);
    }

    @Override
    public void removeCustomer(String customerId) {
        Customer existingCustomer = customerRepository.findByCustomerIdentifier_CustomerId(customerId);

        if (existingCustomer == null) {
            throw new NotFoundException("Unknown customerId: " + customerId);
        }

        customerRepository.delete(existingCustomer);
    }

}
