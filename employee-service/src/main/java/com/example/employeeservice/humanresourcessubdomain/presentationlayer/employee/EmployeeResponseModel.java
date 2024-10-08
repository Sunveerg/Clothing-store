package com.example.employeeservice.humanresourcessubdomain.presentationlayer.employee;

import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.employee.CurrencyType;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.employee.PhoneNumber;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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
