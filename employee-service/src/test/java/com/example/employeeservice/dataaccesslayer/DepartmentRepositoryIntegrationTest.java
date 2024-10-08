package com.example.employeeservice.dataaccesslayer;

import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.Department;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.DepartmentIdentifier;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.DepartmentRepository;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.Position;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.employee.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class DepartmentRepositoryIntegrationTest {

    @Autowired
    DepartmentRepository departmentRepository;

    @BeforeEach
    public void setupDb(){departmentRepository.deleteAll();}

    @Test
    public void whenDepartmentExists_ReturnDepartmentId(){


        Position position = new Position();
        Position position1 = new Position();
        
        ArrayList<Position> positions = new ArrayList<>();
        positions.add(position);
        positions.add(position1);


        Department department1 = new Department(
               "name",
                1,
                positions
        );

        departmentRepository.save(department1);

        Department department = departmentRepository.findDepartmentByDepartmentIdentifier_DepartmentId(department1.getDepartmentIdentifier().getDepartmentId());

        assertNotNull(department);
        assertEquals(department.getDepartmentIdentifier().getDepartmentId(), department1.getDepartmentIdentifier().getDepartmentId());
        assertEquals(department.getName(), department1.getName());
    }

    @Test
    public void whenDepartmentDoesNotExist_ReturnNull(){

        Department savedDepartment = departmentRepository.findDepartmentByDepartmentIdentifier_DepartmentId("1234");

        assertNull(savedDepartment);

    }

}
