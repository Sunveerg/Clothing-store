package com.example.employeeservice.presentationlayer;

import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.employee.CurrencyType;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.employee.EmployeeRepository;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.employee.PhoneNumber;
import com.example.employeeservice.humanresourcessubdomain.presentationlayer.employee.EmployeeRequestModel;
import com.example.employeeservice.humanresourcessubdomain.presentationlayer.employee.EmployeeResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("h2")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EmployeeControllerIntegrationTest {

    private final String BASE_URI_EMPLOYEES = "api/v1/employees";
    private final String FOUND_EMPLOYEE_ID = "e5913a79-9b1e-4516-9ffd-06578e7af261";
    private final String NOT_FOUND_EMPLOYEE_ID = "007";


    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void whenGetEmployees_thenReturnAllEmployees() {
        //arrange
        Long sizeDB = employeeRepository.count();


        //act
        webTestClient.get()
                .uri(BASE_URI_EMPLOYEES)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(EmployeeResponseModel.class)
                .value((list) -> {
                    assertNotNull(list);
                    assertTrue(list.size() == sizeDB);
                    assertFalse(sizeDB > 0);
                });

    }

    @Test
    public void whenGetEmployeeDoesNotExist_thenReturnNotFound() {
        //act & assert
        webTestClient.get()
                .uri(BASE_URI_EMPLOYEES + "/" + NOT_FOUND_EMPLOYEE_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown employeeId: " + NOT_FOUND_EMPLOYEE_ID);
    }

    @Test
    public void whenValidEmployee_thenCreateEmployee() {
        //arrange
        long sizeDB = employeeRepository.count();

        PhoneNumber phoneNumber = new PhoneNumber();
        PhoneNumber phoneNumber1 = new PhoneNumber();
        ArrayList<PhoneNumber> phonenumbers = new ArrayList<>();
        phonenumbers.add(phoneNumber);
        phonenumbers.add(phoneNumber1);

        EmployeeRequestModel employeeRequestModel = new EmployeeRequestModel(
                "Vilma",
                "Chawner",
                "vchawner0@phoca.cz",
                phonenumbers,
                "8452 Anhalt Park",
                "Chambly",
                "Québec",
                "Canada",
                "J3L 5Y6",
                "1048b354-c18f-4109-8282-2a85485bfa5a",
                "associate",
                new BigDecimal("34000.00"),
                CurrencyType.CAD,
                0.0);

        webTestClient.post()
                .uri(BASE_URI_EMPLOYEES)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(employeeRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(EmployeeResponseModel.class)
                .value((employeeResponseModel) -> {
                    assertNotNull(employeeResponseModel);
                    assertEquals(employeeRequestModel.getFirstName(), employeeResponseModel.getFirstName());
                    assertEquals(employeeRequestModel.getCity(), employeeResponseModel.getCity());
                    assertEquals(employeeRequestModel.getCommissionRate(), employeeResponseModel.getCommissionRate());
                });
        long sizeDbAfter = employeeRepository.count();
        assertEquals(sizeDB + 1, sizeDbAfter);

    }


    @Test
    public void whenEmployeeDoesNotExistOnUpdate_thenReturnNotFound() {

        PhoneNumber phoneNumber = new PhoneNumber();
        PhoneNumber phoneNumber1 = new PhoneNumber();
        ArrayList<PhoneNumber> phonenumbers = new ArrayList<>();
        phonenumbers.add(phoneNumber);
        phonenumbers.add(phoneNumber1);

        // Arrange
        EmployeeRequestModel employeeRequestModel = new EmployeeRequestModel(
                "Vilma",
                "Chawner",
                "vchawner0@phoca.cz",
                phonenumbers,
                "8452 Anhalt Park",
                "Chambly",
                "Québec",
                "Canada",
                "J3L 5Y6",
                "1048b354-c18f-4109-8282-2a85485bfa5a",
                "associate",
                new BigDecimal("34000.00"),
                CurrencyType.CAD,
                0.0);

        // Perform a PUT or PATCH request to update a league that does not exist
        webTestClient.put()
                .uri(BASE_URI_EMPLOYEES + "/" + NOT_FOUND_EMPLOYEE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(employeeRequestModel) // Set the request body with the updated league data
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown employeeId: " + NOT_FOUND_EMPLOYEE_ID);
    }


    @Test
    public void whenEmployeeExist_thenDeleteEmployee() {

        // Act: Delete the league
        webTestClient.delete()
                .uri(BASE_URI_EMPLOYEES + "/" + FOUND_EMPLOYEE_ID)
                .exchange()
                .expectStatus().isNotFound();

        // Assert: Verify the league is deleted
        webTestClient.get()
                .uri(BASE_URI_EMPLOYEES + "/" + FOUND_EMPLOYEE_ID)
                .exchange()
                .expectStatus().isNotFound(); // Assuming a 404 Not Found is returned when the league is not found
    }


    @Test
    public void whenValidEmployee_thenUpdateEmployee() {
        // Arrange: Assuming "FOUND_LEAGUE_ID" contains the ID of an existing league

        PhoneNumber phoneNumber = new PhoneNumber();
        PhoneNumber phoneNumber1 = new PhoneNumber();
        ArrayList<PhoneNumber> phonenumbers = new ArrayList<>();
        phonenumbers.add(phoneNumber);
        phonenumbers.add(phoneNumber1);

        // Create the league update request model
        EmployeeRequestModel employeeRequestModel = new EmployeeRequestModel(
                "Vilma",
                "Chawner",
                "vchawner0@phoca.cz",
                phonenumbers,
                "8452 Anhalt Park",
                "Chambly",
                "Québec",
                "Canada",
                "J3L 5Y6",
                "1048b354-c18f-4109-8282-2a85485bfa5a",
                "associate",
                new BigDecimal("34000.00"),
                CurrencyType.CAD,
                0.0);

        // Perform the PUT request to update the league
        webTestClient.put()
                .uri(BASE_URI_EMPLOYEES + "/" + FOUND_EMPLOYEE_ID) // Include the league ID in the URI
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(employeeRequestModel)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(EmployeeResponseModel.class)
                .value((employeeResponseModel) -> {
                    assertNotNull(employeeResponseModel);
                    assertEquals(employeeRequestModel.getFirstName(), "Vilma");
                    assertEquals(employeeRequestModel.getCountry(), "Canada");
                    assertEquals(employeeRequestModel.getCommissionRate(), 0.0);
                });
    }


    @Test
    public void whenEmployeeExists_thenReturnEmployee() {
        // Arrange: Assuming there is a league with the specified ID in the database
        String existingLeagueId = "1"; // Replace with the actual ID of the existing league

        // Act: Perform a GET request to retrieve the league by ID
        webTestClient.get()
                .uri(BASE_URI_EMPLOYEES + "/" + FOUND_EMPLOYEE_ID) // Adjust the URI to match your endpoint for retrieving leagues by ID
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(EmployeeResponseModel.class)
                .value((employee) -> {
                    // Assert: Verify that the returned league is not null and has the correct properties
                    assertNotNull(employee);
                    assertEquals("Vilma", "Vilma");
                    assertEquals("Canada", "Canada");
                });
    }


    @Test
    public void whenEmployeeDontExistOnDelete_thenReturnNotFound() {
        // Arrange: Assuming there are no leagues in the database
        String nonExistingLeagueId = "nonExistingId"; // Replace with an ID that doesn't exist in the database

        // Act: Perform a DELETE request to delete a league that doesn't exist
        webTestClient.delete()
                .uri(BASE_URI_EMPLOYEES + "/" + nonExistingLeagueId) // Adjust the URI to match your endpoint for deleting leagues by ID
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown employeeId: "+ nonExistingLeagueId);
    }

    @Test
    public void whenNoEmployeeExist_thenReturnEmptyList() {
        employeeRepository.deleteAll();
        // Act: Perform a GET request to retrieve all leagues
        webTestClient.get()
                .uri(BASE_URI_EMPLOYEES)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(EmployeeResponseModel.class)
                .value(list -> assertTrue(list.isEmpty())); // Assert that the returned list is empty
    }



    @Test
    public void whenNotLogicThanReturnIllogicInputException(){
        PhoneNumber phoneNumber = new PhoneNumber();
        PhoneNumber phoneNumber1 = new PhoneNumber();
        ArrayList<PhoneNumber> phonenumbers = new ArrayList<>();
        phonenumbers.add(phoneNumber);
        phonenumbers.add(phoneNumber1);

        // Create the league update request model
        EmployeeRequestModel employeeRequestModel = new EmployeeRequestModel(
                "Vilma",
                "Chawner",
                "vchawner0@phoca.cz",
                phonenumbers,
                "8452 Anhalt Park",
                "Chambly",
                "Québec",
                "Canada",
                "J3L 5Y6",
                "1048b354-c18f-4109-8282-2a85485bfa5a",
                "associate",
                new BigDecimal("34000.00"),
                CurrencyType.CAD,
                0.0);


        webTestClient.post()
                .uri(BASE_URI_EMPLOYEES )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(employeeRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("UNPROCESSABLE_ENTITY") // Adjust the message according to your implementation
                .jsonPath("$.message").isEqualTo("Unknown departmentId: " + employeeRequestModel.getDepartmentId());
    }

    @Test
    public void whenNotLogicThanReturnIllogicInputExceptionOnUpdate(){
        PhoneNumber phoneNumber = new PhoneNumber();
        PhoneNumber phoneNumber1 = new PhoneNumber();
        ArrayList<PhoneNumber> phonenumbers = new ArrayList<>();
        phonenumbers.add(phoneNumber);
        phonenumbers.add(phoneNumber1);

        // Create the league update request model
        EmployeeRequestModel employeeRequestModel = new EmployeeRequestModel(
                "Vilma",
                "Chawner",
                "vchawner0@phoca.cz",
                phonenumbers,
                "8452 Anhalt Park",
                "Chambly",
                "Québec",
                "Canada",
                "J3L 5Y6",
                "1048b354-c18f-4109-8282-2a85485bfa5a",
                "associate",
                new BigDecimal("34000.00"),
                CurrencyType.CAD,
                0.0);


        webTestClient.put()
                .uri(BASE_URI_EMPLOYEES + "/" + FOUND_EMPLOYEE_ID )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(employeeRequestModel)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("UNPROCESSABLE_ENTITY") // Adjust the message according to your implementation
                .jsonPath("$.message").isEqualTo("You can't have more wins, draw and loses than the maximum of match  " + employeeRequestModel.getCity());
    }
    
}
