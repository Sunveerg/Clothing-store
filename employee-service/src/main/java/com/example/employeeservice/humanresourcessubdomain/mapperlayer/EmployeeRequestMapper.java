package com.example.employeeservice.humanresourcessubdomain.mapperlayer;

import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.DepartmentIdentifier;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.employee.Address;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.employee.Employee;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.employee.EmployeeIdentifier;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.employee.Salary;
import com.example.employeeservice.humanresourcessubdomain.presentationlayer.employee.EmployeeRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Christine Gerard
 * @created 02/06/2024
 * @project cardealership-ws-2024
 */

@Mapper(componentModel = "spring")
public interface EmployeeRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "employeeRequestModel.phoneNumbers", target = "phoneNumberList")
    @Mapping(source = "employeeRequestModel.title", target = "positionTitle")
    @Mapping(expression = "java(salary)", target = "salary")
    Employee requestModelToEntity(EmployeeRequestModel employeeRequestModel, EmployeeIdentifier employeeIdentifier,
                                  DepartmentIdentifier departmentIdentifier, Address address, Salary salary);
}
