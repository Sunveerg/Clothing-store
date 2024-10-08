package com.example.employeeservice.humanresourcessubdomain.mapperlayer;

import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.Department;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.DepartmentIdentifier;
import com.example.employeeservice.humanresourcessubdomain.presentationlayer.department.DepartmentRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Christine Gerard
 * @created 02/05/2024
 * @project cardealership-ws-2024
 */

@Mapper(componentModel = "spring")
public interface DepartmentRequestMapper {

    @Mapping(target = "id", ignore = true)
    //@Mapping(expression = "java(departmentIdentifier)", target = "departmentIdentifier")
    Department requestModelToEntity(DepartmentRequestModel departmentRequestModel, DepartmentIdentifier departmentIdentifier);

}
