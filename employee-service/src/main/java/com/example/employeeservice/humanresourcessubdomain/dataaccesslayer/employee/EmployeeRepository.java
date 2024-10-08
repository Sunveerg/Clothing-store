package com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.employee;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Christine Gerard
 * @created 01/30/2024
 * @project cardealership-ws-2024
 */

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findEmployeeByEmployeeIdentifier_EmployeeId(String employeeId);
}
