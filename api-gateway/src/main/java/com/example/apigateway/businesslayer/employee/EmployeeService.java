package com.example.apigateway.businesslayer.employee;




import com.example.apigateway.presentationlayer.employee.EmployeeRequestModel;
import com.example.apigateway.presentationlayer.employee.EmployeeResponseModel;

import java.util.List;

/**
 * @author Christine Gerard
 * @created 02/02/2024
 * @project cardealership-ws-2024
 */


public interface EmployeeService {

    List<EmployeeResponseModel> getAllEmployees();
    EmployeeResponseModel getEmployeeByEmployeeId(String employeeId);
    EmployeeResponseModel addEmployee(EmployeeRequestModel employeeRequestModel);
    EmployeeResponseModel updateEmployee(EmployeeRequestModel employeeRequestModel, String employeeId);
    void removeEmployee(String employeeId);
}
