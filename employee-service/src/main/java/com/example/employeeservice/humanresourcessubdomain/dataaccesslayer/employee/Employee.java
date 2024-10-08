package com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.employee;

import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.DepartmentIdentifier;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Christine Gerard
 * @created 01/30/2024
 * @project cardealership-ws-2024
 */

@Entity
@Table(name="employees")
@Data
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private EmployeeIdentifier employeeIdentifier;

    @Embedded
    private Address address;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "employee_phonenumbers", joinColumns = @JoinColumn(name="employee_id"))
    private List<PhoneNumber> phoneNumberList;

    private String firstName;
    private String lastName;
    private String emailAddress;

    @Embedded
    private Salary salary;

    private Double commissionRate;

    @Embedded
    private DepartmentIdentifier departmentIdentifier;

    private String positionTitle;

    public Employee(@NotNull Address address, @NotNull List<PhoneNumber> phoneNumberList, @NotNull String firstName,
                    @NotNull String lastName, @NotNull String email, @NotNull Salary salary, @NotNull Double commissionRate,
                    @NotNull DepartmentIdentifier departmentIdentifier, @NotNull String title) {
        this.employeeIdentifier = new EmployeeIdentifier();
        this.address = address;
        this.phoneNumberList = phoneNumberList;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = email;
        this.salary = salary;
        this.commissionRate = commissionRate;
        this.departmentIdentifier = departmentIdentifier;
        this.positionTitle = title;
    }
}
