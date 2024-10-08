package com.example.employeeservice.humanresourcessubdomain.mapperlayer;

import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.Department;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.employee.Employee;
import com.example.employeeservice.humanresourcessubdomain.presentationlayer.employee.EmployeeController;
import com.example.employeeservice.humanresourcessubdomain.presentationlayer.employee.EmployeeResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Mapper(componentModel = "spring")
public interface EmployeeResponseMapper {

    @Mapping(expression = "java(employee.getEmployeeIdentifier().getEmployeeId())", target = "employeeId")
    @Mapping(expression = "java(employee.getAddress().getStreetAddress())", target = "streetAddress")
    @Mapping(expression = "java(employee.getAddress().getCity())", target = "city")
    @Mapping(expression = "java(employee.getAddress().getProvince())", target = "province")
    @Mapping(expression = "java(employee.getAddress().getCountry())", target = "country")
    @Mapping(expression = "java(employee.getAddress().getPostalCode())", target = "postalCode")
    @Mapping(source = "employee.phoneNumberList", target = "phoneNumbers")
    @Mapping(source = "employee.positionTitle", target = "title")
    @Mapping(expression = "java(employee.getSalary().getSalary())", target = "salary")
    @Mapping(expression = "java(employee.getSalary().getCurrency())", target = "currency")
    @Mapping(expression = "java(department.getDepartmentIdentifier().getDepartmentId())", target = "departmentId")
    @Mapping(expression = "java(department.getName())", target = "departmentName")
    EmployeeResponseModel entityToResponseModel(Employee employee, Department department);

    @AfterMapping
    default void addLinks(@MappingTarget EmployeeResponseModel model, Employee employee) {
        Link selfLink = linkTo(methodOn(EmployeeController.class).
                getEmployeeByEmployeeId(model.getEmployeeId())).withSelfRel();
        model.add(selfLink);
    }
}
