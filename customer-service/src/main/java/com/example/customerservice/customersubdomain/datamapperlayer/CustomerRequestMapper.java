
package com.example.customerservice.customersubdomain.datamapperlayer;


import com.example.customerservice.customersubdomain.datalayer.Address;
import com.example.customerservice.customersubdomain.datalayer.Customer;
import com.example.customerservice.customersubdomain.datalayer.CustomerIdentifier;
import com.example.customerservice.customersubdomain.presentationlayer.CustomerRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface CustomerRequestMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true),
    })
    Customer requestModelToEntity(CustomerRequestModel customerRequestModel, CustomerIdentifier customerIdentifier, Address address);
}
