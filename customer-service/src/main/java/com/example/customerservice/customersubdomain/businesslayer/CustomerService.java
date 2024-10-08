package com.example.customerservice.customersubdomain.businesslayer;





import com.example.customerservice.customersubdomain.presentationlayer.CustomerRequestModel;
import com.example.customerservice.customersubdomain.presentationlayer.CustomerResponseModel;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    List<CustomerResponseModel> getCustomers();
    CustomerResponseModel getCustomerByCustomerId(String customerId);
    CustomerResponseModel addCustomer(CustomerRequestModel customerRequestModel);
    CustomerResponseModel updateCustomer(CustomerRequestModel updatedCustomer, String customerId);
    void removeCustomer(String customerId);

}
