package com.example.customerservice.customersubdomain.presentationlayer;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public final class CustomerResponseModel extends RepresentationModel<CustomerResponseModel>{

    String customerId;
    String firstName;
    String lastName;
    String emailAddress;
    String streetAddress;
    String city;
    String province;
    String country;
    String postalCode;

}
