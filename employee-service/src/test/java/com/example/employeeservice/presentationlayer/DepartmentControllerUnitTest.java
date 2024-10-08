package com.example.employeeservice.presentationlayer;


import com.example.employeeservice.humanresourcessubdomain.businesslayer.department.DepartmentService;
import com.example.employeeservice.humanresourcessubdomain.presentationlayer.department.DepartmentController;
import com.example.employeeservice.humanresourcessubdomain.presentationlayer.department.DepartmentRequestModel;
import com.example.employeeservice.humanresourcessubdomain.presentationlayer.department.DepartmentResponseModel;
import com.example.employeeservice.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = DepartmentController.class)
public class DepartmentControllerUnitTest {

    @Autowired
    DepartmentController departmentController;

    @MockBean
    DepartmentService departmentService;

    private final String FOUND_DEPARTMENT_ID = "f1d5846b-ea42-4ec4-a414-cb132c193154";
    private final String NOT_FOUND_DEPARTMENT_ID = "100";


    @Test
    public void whenNodepartmentExists_thenReturnEmptyList(){
        //arrange
        when(departmentService.getAllDepartments()).thenReturn(Collections.EMPTY_LIST);

        //act
        ResponseEntity<List<DepartmentResponseModel>> responseEntity= departmentController.getAllDepartments();

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(departmentService, times(1)).getAllDepartments();
    }

    @Test
    public void whendepartmentExists_thenReturndepartment(){
        //arrange

        DepartmentRequestModel departmentRequestModel = DepartmentRequestModel.builder().build();
        DepartmentResponseModel departmentResponseModel= DepartmentResponseModel.builder().build();

        when(departmentService.addDepartment(departmentRequestModel)).thenReturn(departmentResponseModel);

        //act
        ResponseEntity<DepartmentResponseModel> responseEntity= departmentController.addDepartment(departmentRequestModel);

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(departmentResponseModel, responseEntity.getBody());
        verify(departmentService, times(1)).addDepartment(departmentRequestModel);
    }

    @Test
    public void whendepartmentExists_thenDeletedepartment() throws NotFoundException {
        // Arrange
        doNothing().when(departmentService).removeDepartmentByDepartmentId(FOUND_DEPARTMENT_ID);

        // Act
        ResponseEntity<Void> responseEntity = departmentController.removeDepartment(FOUND_DEPARTMENT_ID);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(departmentService, times(1)).removeDepartmentByDepartmentId(FOUND_DEPARTMENT_ID);
    }

    @Test
    public void WhendepartmentDoesNotExistOnDelete_thenReturnNotFoundException() throws NotFoundException {
        // Arrange
        String nonExistentDepartmentId = "non-existent-department-id";
        doThrow(NotFoundException.class).when(departmentService).removeDepartmentByDepartmentId(nonExistentDepartmentId);

        // Act and Assert
        try {
            departmentController.removeDepartment(nonExistentDepartmentId);
            fail("Expected NotFoundException");
        } catch (NotFoundException e) {
            // Verify that NotFoundException was thrown
            verify(departmentService, times(1)).removeDepartmentByDepartmentId(nonExistentDepartmentId);
        }
    }

    @Test
    public void whendepartmentNotFoundOnGet_thenReturnNotFoundException() {
        // Arrange
        when(departmentService.getDepartmentByDepartmentId(NOT_FOUND_DEPARTMENT_ID)).thenThrow(NotFoundException.class);

        // Act and Assert
        try {
            departmentController.getDepartmentByDepartmentId(NOT_FOUND_DEPARTMENT_ID);
            fail("Expected NotFoundException");
        } catch (NotFoundException e) {
            // Verify that NotFoundException was thrown
            verify(departmentService, times(1)).getDepartmentByDepartmentId(NOT_FOUND_DEPARTMENT_ID);
        }
    }

    @Test
    public void whendepartmentNotExistOnUpdate_thenReturnNotFoundException() throws NotFoundException {
        // Arrange
        DepartmentRequestModel updateddepartment = new DepartmentRequestModel();
        when(departmentService.updateDepartment(updateddepartment, NOT_FOUND_DEPARTMENT_ID)).thenThrow(NotFoundException.class);

        // Act and Assert
        try {
            departmentController.updateDepartment(updateddepartment, NOT_FOUND_DEPARTMENT_ID);
            fail("Expected NotFoundException");
        } catch (NotFoundException e) {
            // Verify that NotFoundException was thrown
            verify(departmentService, times(1)).updateDepartment(updateddepartment, NOT_FOUND_DEPARTMENT_ID);
        }
    }


    @Test
    public void whendepartmentExist_thenReturnUpdatedepartment() throws NotFoundException {
        // Arrange
        String existingdepartmentId = "existing-department-id";
        DepartmentRequestModel updateddepartment = new DepartmentRequestModel();
        DepartmentResponseModel updatedResponse = DepartmentResponseModel.builder().departmentId(FOUND_DEPARTMENT_ID).build();

        when(departmentService.updateDepartment(updateddepartment, FOUND_DEPARTMENT_ID)).thenReturn(updatedResponse);

        // Act
        ResponseEntity<DepartmentResponseModel> responseEntity = departmentController.updateDepartment(updateddepartment, FOUND_DEPARTMENT_ID);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(FOUND_DEPARTMENT_ID, responseEntity.getBody().getDepartmentId());
        verify(departmentService, times(1)).updateDepartment(updateddepartment, FOUND_DEPARTMENT_ID);
    }
    
}
