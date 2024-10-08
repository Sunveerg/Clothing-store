package com.example.employeeservice.humanresourcessubdomain.mapperlayer;

import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.Department;
import com.example.employeeservice.humanresourcessubdomain.presentationlayer.department.DepartmentController;
import com.example.employeeservice.humanresourcessubdomain.presentationlayer.department.DepartmentResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Mapper(componentModel = "spring")
public interface DepartmentResponseMapper {

    @Mapping(expression = "java(department.getDepartmentIdentifier().getDepartmentId())", target = "departmentId")
    DepartmentResponseModel entityToResponseModel(Department department);

    List<DepartmentResponseModel> entityListToResponseModelList(List<Department> departments);

    @AfterMapping
    default void addLinks(@MappingTarget DepartmentResponseModel model, Department department) {
        Link selfLink = linkTo(methodOn(DepartmentController.class).
                getDepartmentByDepartmentId(model.getDepartmentId())).withSelfRel();
        model.add(selfLink);
    }
}
