package com.example.employeeservice.humanresourcessubdomain.presentationlayer.department;

import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.Position;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DepartmentResponseModel extends RepresentationModel<DepartmentResponseModel> {

    String departmentId;
    String name;
    Integer headCount;

    List<Position> positions;

}
