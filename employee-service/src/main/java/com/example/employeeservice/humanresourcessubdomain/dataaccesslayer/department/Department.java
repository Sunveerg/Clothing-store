package com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author Christine Gerard
 * @created 01/30/2024
 * @project cardealership-ws-2024
 */

@Entity
@Table(name = "departments")
@Data
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private DepartmentIdentifier departmentIdentifier;

    private String name;
    private Integer headCount;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "department_positions", joinColumns = @JoinColumn(name = "department_id"))
    private List<Position> positions;

    public Department() {}

    public Department(@NotNull String name, @NotNull Integer headCount, @NotNull List<Position> positions) {
        this.departmentIdentifier = new DepartmentIdentifier();
        this.name = name;
        this.headCount = headCount;
        this.positions = positions;
    }
}
