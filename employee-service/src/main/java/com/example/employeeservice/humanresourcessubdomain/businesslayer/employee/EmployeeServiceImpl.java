package com.example.employeeservice.humanresourcessubdomain.businesslayer.employee;


import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.Department;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.DepartmentRepository;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.employee.*;
import com.example.employeeservice.humanresourcessubdomain.mapperlayer.EmployeeRequestMapper;
import com.example.employeeservice.humanresourcessubdomain.mapperlayer.EmployeeResponseMapper;
import com.example.employeeservice.humanresourcessubdomain.presentationlayer.employee.EmployeeRequestModel;
import com.example.employeeservice.humanresourcessubdomain.presentationlayer.employee.EmployeeResponseModel;
import com.example.employeeservice.utils.exceptions.InvalidInputException;
import com.example.employeeservice.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Christine Gerard
 * @created 02/02/2024
 * @project cardealership-ws-2024
 */

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeResponseMapper employeeResponseMapper;
    private final EmployeeRequestMapper employeeRequestMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, EmployeeResponseMapper employeeResponseMapper, EmployeeRequestMapper employeeRequestMapper) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.employeeResponseMapper = employeeResponseMapper;
        this.employeeRequestMapper = employeeRequestMapper;
    }


    @Override
    public List<EmployeeResponseModel> getAllEmployees() {

        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponseModel> employeeResponseModelList = new ArrayList<>();

        employees.forEach(employee -> {
            /* EmployeeResponseModel employeeResponseModel = new EmployeeResponseModel();
            BeanUtils.copyProperties(employee, employeeResponseModel);
            employeeResponseModel.setEmployeeId(employee.getEmployeeIdentifier().getEmployeeId());
            employeeResponseModel.setStreetAddress(employee.getAddress().getStreetAddress());
            employeeResponseModel.setCity(employee.getAddress().getCity());
            employeeResponseModel.setProvince(employee.getAddress().getProvince());
            employeeResponseModel.setPostalCode(employee.getAddress().getPostalCode());
            employeeResponseModel.setCountry(employee.getAddress().getCountry());
            employeeResponseModel.setPhoneNumbers(employee.getPhoneNumberList());
            employeeResponseModel.setSalary(employee.getSalary().getSalary());
            employeeResponseModel.setCurrency(employee.getSalary().getCurrency());
            //get and set department name
            Department department = departmentRepository.findDepartmentByDepartmentIdentifier_DepartmentId(employee.getDepartmentIdentifier().getDepartmentId());
            employeeResponseModel.setDepartmentName(department.getName());
            employeeResponseModel.setTitle(employee.getPositionTitle()); */

            Department department = departmentRepository.findDepartmentByDepartmentIdentifier_DepartmentId(employee.getDepartmentIdentifier().getDepartmentId());

            employeeResponseModelList.add(employeeResponseMapper.entityToResponseModel(employee, department));
        });

        return employeeResponseModelList;
    }

    @Override
    public EmployeeResponseModel getEmployeeByEmployeeId(String employeeId) {

        Employee foundEmployee = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId(employeeId);

        if (foundEmployee == null) {
            throw new NotFoundException("Unknown employeeId: " + employeeId);
        }

        /*
        EmployeeResponseModel employeeResponseModel = new EmployeeResponseModel();

        BeanUtils.copyProperties(foundEmployee, employeeResponseModel);
        employeeResponseModel.setEmployeeId(foundEmployee.getEmployeeIdentifier().getEmployeeId());
        employeeResponseModel.setStreetAddress(foundEmployee.getAddress().getStreetAddress());
        employeeResponseModel.setCity(foundEmployee.getAddress().getCity());
        employeeResponseModel.setProvince(foundEmployee.getAddress().getProvince());
        employeeResponseModel.setPostalCode(foundEmployee.getAddress().getPostalCode());
        employeeResponseModel.setCountry(foundEmployee.getAddress().getCountry());
        employeeResponseModel.setPhoneNumbers(foundEmployee.getPhoneNumberList());
        employeeResponseModel.setSalary(foundEmployee.getSalary().getSalary());
        employeeResponseModel.setCurrency(foundEmployee.getSalary().getCurrency());
        //get and set department name
        Department department = departmentRepository.findDepartmentByDepartmentIdentifier_DepartmentId(foundEmployee.getDepartmentIdentifier().getDepartmentId());
        employeeResponseModel.setDepartmentId(department.getDepartmentIdentifier().getDepartmentId());
        employeeResponseModel.setDepartmentName(department.getName());
        employeeResponseModel.setTitle(foundEmployee.getPositionTitle());

        return employeeResponseModel; */
        Department department = departmentRepository.findDepartmentByDepartmentIdentifier_DepartmentId(foundEmployee.getDepartmentIdentifier().getDepartmentId());

        return employeeResponseMapper.entityToResponseModel(foundEmployee, department);
    }

    @Override
    public EmployeeResponseModel addEmployee(EmployeeRequestModel employeeRequestModel) {

        Department department = departmentRepository.findDepartmentByDepartmentIdentifier_DepartmentId(employeeRequestModel.getDepartmentId());

        //does department exist?
        if (department == null) {
            throw new InvalidInputException("Unknown departmentId: " + employeeRequestModel.getDepartmentId());
        }

        //does position exist within given department?
        AtomicBoolean found = new AtomicBoolean(false);
        department.getPositions().forEach(position -> {
            if (Objects.equals(position.getTitle(), employeeRequestModel.getTitle())) {
                found.set(true);
            }
        });

        if (!found.get()) {
            throw new InvalidInputException("Title \"" + employeeRequestModel.getTitle() + "\" not found for the provided department");
        }

        //create Employee
        /*
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequestModel, employee);
        //create Address object
        Address address = new Address(employeeRequestModel.getStreetAddress(), employeeRequestModel.getCity(),
            employeeRequestModel.getProvince(), employeeRequestModel.getCountry(), employeeRequestModel.getPostalCode());
        employee.setAddress(address);
        employee.setEmployeeIdentifier(new EmployeeIdentifier());
        employee.setDepartmentIdentifier(department.getDepartmentIdentifier());
        Salary salary = new Salary(employeeRequestModel.getSalary(), employeeRequestModel.getCurrency());
        employee.setSalary(salary);
        employee.setPhoneNumberList(employeeRequestModel.getPhoneNumbers());
        employee.setPositionTitle(employeeRequestModel.getTitle());
        */



        Employee savedEmployee = employeeRepository.save(employeeRequestMapper
            .requestModelToEntity(employeeRequestModel,
                new EmployeeIdentifier(),
                department.getDepartmentIdentifier(),
                new Address(employeeRequestModel.getStreetAddress(), employeeRequestModel.getCity(),
            employeeRequestModel.getProvince(), employeeRequestModel.getCountry(), employeeRequestModel.getPostalCode()),
                new Salary(employeeRequestModel.getSalary(), employeeRequestModel.getCurrency())));

        /*
        EmployeeResponseModel employeeResponseModel = new EmployeeResponseModel();
        BeanUtils.copyProperties(savedEmployee, employeeResponseModel);
        employeeResponseModel.setEmployeeId(savedEmployee.getEmployeeIdentifier().getEmployeeId());
        employeeResponseModel.setStreetAddress(savedEmployee.getAddress().getStreetAddress());
        employeeResponseModel.setCity(savedEmployee.getAddress().getCity());
        employeeResponseModel.setProvince(savedEmployee.getAddress().getProvince());
        employeeResponseModel.setCountry(savedEmployee.getAddress().getCountry());
        employeeResponseModel.setPostalCode(savedEmployee.getAddress().getPostalCode());
        employeeResponseModel.setDepartmentId(savedEmployee.getDepartmentIdentifier().getDepartmentId());
        employeeResponseModel.setDepartmentName(department.getName());
        employeeResponseModel.setSalary(savedEmployee.getSalary().getSalary());
        employeeResponseModel.setCurrency(savedEmployee.getSalary().getCurrency());
        employeeResponseModel.setPhoneNumbers(savedEmployee.getPhoneNumberList());
        employeeResponseModel.setTitle(savedEmployee.getPositionTitle()); */

        return employeeResponseMapper.entityToResponseModel(savedEmployee, department);
    }

    @Override
    public EmployeeResponseModel updateEmployee(EmployeeRequestModel employeeRequestModel, String employeeId) {

        //does employee exist?
        Employee foundEmployee = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId(employeeId);

        if (foundEmployee == null) {
            throw new NotFoundException("Unknown employeeId: " + employeeId);
        }

        Department department = departmentRepository.findDepartmentByDepartmentIdentifier_DepartmentId(employeeRequestModel.getDepartmentId());

        //does department exist?
        if (department == null) {
            throw new InvalidInputException("Unknown departmentId: " + employeeRequestModel.getDepartmentId());
        }

        //does position exist within given department?
        AtomicBoolean found = new AtomicBoolean(false);
        department.getPositions().forEach(position -> {
            if (Objects.equals(position.getTitle(), employeeRequestModel.getTitle())) {
                found.set(true);
            }
        });

        if (!found.get()) {
            throw new InvalidInputException("Title \"" + employeeRequestModel.getTitle() + "\" not found for the provided department");
        }

        Employee savedEmployee = employeeRepository.save(employeeRequestMapper
            .requestModelToEntity(employeeRequestModel,
                foundEmployee.getEmployeeIdentifier(),
                department.getDepartmentIdentifier(),
                new Address(employeeRequestModel.getStreetAddress(), employeeRequestModel.getCity(),
                    employeeRequestModel.getProvince(), employeeRequestModel.getCountry(), employeeRequestModel.getPostalCode()),
                new Salary(employeeRequestModel.getSalary(), employeeRequestModel.getCurrency())));

        return employeeResponseMapper.entityToResponseModel(savedEmployee, department);
    }

    @Override
    public void removeEmployee(String employeeId) {

       //does employee exist?
            Employee foundEmployee = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId(employeeId);

        if (foundEmployee == null) {
            throw new NotFoundException("Unknown employeeId: " + employeeId);
        }

        employeeRepository.delete(foundEmployee);

    }


}
