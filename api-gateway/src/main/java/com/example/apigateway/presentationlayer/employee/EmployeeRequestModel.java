package com.example.apigateway.presentationlayer.employee;


import com.example.apigateway.domainclientlayer.employee.CurrencyType;
import com.example.apigateway.domainclientlayer.employee.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Christine Gerard
 * @created 02/06/2024
 * @project cardealership-ws-2024
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestModel {

    String firstName;
    String lastName;
    String emailAddress;
    List<PhoneNumber> phoneNumbers;

    String streetAddress;
    String city;
    String province;
    String country;
    String postalCode;

    String departmentId;
    String title;

    BigDecimal salary;
    CurrencyType currency;
    Double commissionRate;
}
