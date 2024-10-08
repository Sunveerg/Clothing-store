package com.example.employeeservice.presentationlayer;

import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.Department;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.DepartmentRepository;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.Position;
import com.example.employeeservice.humanresourcessubdomain.presentationlayer.department.DepartmentRequestModel;
import com.example.employeeservice.humanresourcessubdomain.presentationlayer.department.DepartmentResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("h2")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DepartmentControllerIntegrationTest {

    private final String BASE_URI_DEPARTMENTS = "/api/v1/departments";
    private final String FOUND_DEPARTMENT_ID = "1";
    private final String NOT_FOUND_DEPARTMENT_ID = "100";

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void whenGetDepartments_thenReturnAllDepartments() {
        //arrange
        Long sizeDB = departmentRepository.count();
        System.out.println("size of H2 db : " + sizeDB);
        //act
        webTestClient.get()
                .uri(BASE_URI_DEPARTMENTS)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(DepartmentResponseModel.class)
                .value((list) -> {
                    assertNotNull(list);
                    assertTrue(list.size() == sizeDB);
                });

    }

    @Test
    public void whenGetDepartmentDoesNotExist_thenReturnNotFound() {
        //act & assert
        webTestClient.get()
                .uri(BASE_URI_DEPARTMENTS + "/" + NOT_FOUND_DEPARTMENT_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown departmentId " + NOT_FOUND_DEPARTMENT_ID);
    }

    @Test
    public void whenValidDepartment_thenCreateDepartment() {
        //arrange
        long sizeDB = departmentRepository.count();

        Position position = new Position();
        Position position1 = new Position();

        ArrayList<Position> positions = new ArrayList<>();
        positions.add(position);
        positions.add(position1);


        DepartmentRequestModel departmentRequestModel = new DepartmentRequestModel(
                "name",
                1,
                positions
        );

        webTestClient.post()
                .uri(BASE_URI_DEPARTMENTS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(departmentRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DepartmentResponseModel.class)
                .value((DepartmentResponseModel) -> {
                    assertNotNull(DepartmentResponseModel);
                    assertEquals(departmentRequestModel.getHeadCount(), DepartmentResponseModel.getHeadCount());
                    assertEquals(departmentRequestModel.getName(), DepartmentResponseModel.getName());
                });
        long sizeDbAfter = departmentRepository.count();
        assertEquals(sizeDB + 1, sizeDbAfter);

    }


    @Test
    public void whenDepartmentDoesNotExistOnUpdate_thenReturnNotFound() {
        // Arrange
        Position position = new Position();
        Position position1 = new Position();

        ArrayList<Position> positions = new ArrayList<>();
        positions.add(position);
        positions.add(position1);


        DepartmentRequestModel departmentRequestModel = new DepartmentRequestModel(
                "name",
                1,
                positions
        );

        // Perform a PUT or PATCH request to update a Department that does not exist
        webTestClient.put()
                .uri(BASE_URI_DEPARTMENTS + "/" + NOT_FOUND_DEPARTMENT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(departmentRequestModel) // Set the request body with the updated Department data
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown departmentId " + NOT_FOUND_DEPARTMENT_ID);
    }


    @Test
    public void whenDepartmentExist_thenDeleteDepartment() {
        // Arrange: Ensure that the Department exists in the database and retrieve its ID
        String existingDepartmentId = "1"; // Replace with the actual ID of the existing Department

        // Act: Delete the Department
        webTestClient.delete()
                .uri(BASE_URI_DEPARTMENTS + "/" + FOUND_DEPARTMENT_ID)
                .exchange()
                .expectStatus().isNoContent();

        // Assert: Verify the Department is deleted
        webTestClient.get()
                .uri(BASE_URI_DEPARTMENTS + "/" + FOUND_DEPARTMENT_ID)
                .exchange()
                .expectStatus().isNotFound(); // Assuming a 404 Not Found is returned when the Department is not found
    }


    @Test
    public void whenValidDepartment_thenUpdateDepartment() {
        // Arrange: Assuming "FOUND_DEPARTMENT_ID" contains the ID of an existing Department
        String DepartmentIdToUpdate = FOUND_DEPARTMENT_ID;

        // Create the Department update request model
        Position position = new Position();
        Position position1 = new Position();

        ArrayList<Position> positions = new ArrayList<>();
        positions.add(position);
        positions.add(position1);


        DepartmentRequestModel departmentRequestModel = new DepartmentRequestModel(
                "name",
                1,
                positions
        );

        // Perform the PUT request to update the Department
        webTestClient.put()
                .uri(BASE_URI_DEPARTMENTS + "/" + DepartmentIdToUpdate) // Include the Department ID in the URI
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(departmentRequestModel)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DepartmentResponseModel.class)
                .value((DepartmentResponseModel) -> {
                    assertNotNull(DepartmentResponseModel);
                    assertEquals(departmentRequestModel.getHeadCount(), DepartmentResponseModel.getHeadCount());
                    assertEquals(departmentRequestModel.getName(), DepartmentResponseModel.getName());
                });
    }


    @Test
    public void whenDepartmentExists_thenReturnDepartment() {
        // Arrange: Assuming there is a Department with the specified ID in the database
        String existingDepartmentId = "1"; // Replace with the actual ID of the existing Department

        // Act: Perform a GET request to retrieve the Department by ID
        webTestClient.get()
                .uri(BASE_URI_DEPARTMENTS + "/" + FOUND_DEPARTMENT_ID) // Adjust the URI to match your endpoint for retrieving Departments by ID
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DepartmentResponseModel.class)
                .value((Department) -> {
                    // Assert: Verify that the returned Department is not null and has the correct properties
                    assertNotNull(Department);
                    assertEquals("name", Department.getName());
                });
    }


    @Test
    public void whenDepartmentsDontExistOnDelete_thenReturnNotFound() {
        // Arrange: Assuming there are no Departments in the database
        String nonExistingDepartmentId = "nonExistingId"; // Replace with an ID that doesn't exist in the database

        // Act: Perform a DELETE request to delete a Department that doesn't exist
        webTestClient.delete()
                .uri(BASE_URI_DEPARTMENTS + "/" + nonExistingDepartmentId) // Adjust the URI to match your endpoint for deleting Departments by ID
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown departmentId " + nonExistingDepartmentId);
    }

    @Test
    public void whenNoDepartmentExist_thenReturnEmptyList() {
        departmentRepository.deleteAll();
        // Act: Perform a GET request to retrieve all Departments
        webTestClient.get()
                .uri(BASE_URI_DEPARTMENTS)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(DepartmentResponseModel.class)
                .value(list -> assertTrue(list.isEmpty())); // Assert that the returned list is empty
    }
    
}
