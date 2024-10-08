package com.example.apigateway.businesslayer.employee;

import com.example.apigateway.domainclientlayer.employee.EmployeeServiceClient;
import com.example.apigateway.mapperlayer.employee.EmployeeResponseMapper;
import com.example.apigateway.presentationlayer.employee.EmployeeRequestModel;
import com.example.apigateway.presentationlayer.employee.EmployeeResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeResponseMapper employeeResponseMapper;
    private final EmployeeServiceClient employeeServiceClient;

    public EmployeeServiceImpl(EmployeeResponseMapper employeeResponseMapper, EmployeeServiceClient employeeServiceClient) {
        this.employeeResponseMapper = employeeResponseMapper;
        this.employeeServiceClient = employeeServiceClient;
    }

    @Override
    public List<EmployeeResponseModel> getAllEmployees() {
        return employeeResponseMapper.entityListToResponseModelList(employeeServiceClient.getAllEmployees());
    }

    @Override
    public EmployeeResponseModel getEmployeeByEmployeeId(String employeeId) {
        return employeeResponseMapper.entityToResponseModel(employeeServiceClient.getEmployeeByEmployeeId(employeeId));
    }

    @Override
    public void removeEmployee(String employeeId) {
        employeeServiceClient.removeEmployee(employeeId);
    }

    @Override
    public EmployeeResponseModel addEmployee(EmployeeRequestModel employeeRequestModel) {
        return  employeeResponseMapper.entityToResponseModel(employeeServiceClient.addEmployee(employeeRequestModel));
    }

    @Override
    public EmployeeResponseModel updateEmployee(EmployeeRequestModel employeeRequestModel, String employeeId) {
        return employeeResponseMapper.entityToResponseModel(employeeServiceClient.updateEmployee(employeeId,employeeRequestModel));
    }
    
}
