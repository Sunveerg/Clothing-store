package com.example.employeeservice.humanresourcessubdomain.presentationlayer.department;

import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Christine Gerard
 * @created 02/02/2024
 * @project cardealership-ws-2024
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequestModel {

    String name;
    Integer headCount;

    List<Position> positions;
}
