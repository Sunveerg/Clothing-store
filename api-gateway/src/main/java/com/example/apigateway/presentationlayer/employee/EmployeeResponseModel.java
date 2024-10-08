package com.example.apigateway.presentationlayer.employee;

import com.example.apigateway.domainclientlayer.employee.CurrencyType;
import com.example.apigateway.domainclientlayer.employee.PhoneNumber;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeResponseModel extends RepresentationModel<EmployeeResponseModel> {

    String employeeId;
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
    String departmentName; //comes from DepartmentIdentifier

    String title;

    BigDecimal salary;
    CurrencyType currency;
    Double commissionRate;


}
