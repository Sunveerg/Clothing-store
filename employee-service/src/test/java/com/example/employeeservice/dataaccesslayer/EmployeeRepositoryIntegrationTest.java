package com.example.employeeservice.dataaccesslayer;

import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.DepartmentIdentifier;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.employee.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

@DataJpaTest
public class EmployeeRepositoryIntegrationTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    public void setupDb(){employeeRepository.deleteAll();}

    @Test
    public void whenEmployeeExists_ReturnEmployeeId(){

        Address address = new Address();
        PhoneNumber phoneNumber = new PhoneNumber();
        PhoneNumber phoneNumber1 = new PhoneNumber();
        ArrayList<PhoneNumber> phonenumbers = new ArrayList<>();
        phonenumbers.add(phoneNumber);
        phonenumbers.add(phoneNumber1);
        Salary salary = new Salary();
        DepartmentIdentifier departmentIdentifier = new DepartmentIdentifier();

        Employee employee1 = new Employee(
                address,
                phonenumbers,
                "John",
                "Doe",
                "jd@hotmail.com",
                salary,
                1.1,
                departmentIdentifier,
                "Manager"
        );

        employeeRepository.save(employee1);

        Employee employee = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId(employee1.getEmployeeIdentifier().getEmployeeId());

        assertNotNull(employee);
        assertEquals(employee.getEmployeeIdentifier().getEmployeeId(), employee1.getEmployeeIdentifier().getEmployeeId());
        assertEquals(employee.getFirstName(), employee1.getFirstName());
    }

    @Test
    public void whenEmployeeDoesNotExist_ReturnNull(){

        Employee savedEmployee = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId("1234");

        assertNull(savedEmployee);

    }
}
