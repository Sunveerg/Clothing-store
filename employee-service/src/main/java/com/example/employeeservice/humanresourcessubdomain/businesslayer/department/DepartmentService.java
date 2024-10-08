package com.example.employeeservice.humanresourcessubdomain.businesslayer.department;


import com.example.employeeservice.humanresourcessubdomain.presentationlayer.department.DepartmentRequestModel;
import com.example.employeeservice.humanresourcessubdomain.presentationlayer.department.DepartmentResponseModel;

import java.util.List;

public interface DepartmentService {

    List<DepartmentResponseModel> getAllDepartments();
    DepartmentResponseModel getDepartmentByDepartmentId(String departmentId);
    DepartmentResponseModel addDepartment(DepartmentRequestModel departmentRequestModel);
    DepartmentResponseModel updateDepartment(DepartmentRequestModel departmentRequestModel, String departmentId);
    void removeDepartmentByDepartmentId(String departmentId);
}
