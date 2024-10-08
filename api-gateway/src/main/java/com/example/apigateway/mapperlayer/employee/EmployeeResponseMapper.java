package com.example.apigateway.mapperlayer.employee;



import com.example.apigateway.presentationlayer.employee.EmployeeController;
import com.example.apigateway.presentationlayer.employee.EmployeeResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Mapper(componentModel = "spring")
public interface EmployeeResponseMapper {


    EmployeeResponseModel entityToResponseModel(EmployeeResponseModel employee);

    List<EmployeeResponseModel> entityListToResponseModelList(List<EmployeeResponseModel> leagues);

    @AfterMapping
    default void addLinks(@MappingTarget EmployeeResponseModel model) {
        Link selfLink = linkTo(methodOn(EmployeeController.class).
                getEmployeeByEmployeeId(model.getEmployeeId())).withSelfRel();
        model.add(selfLink);
    }
}
