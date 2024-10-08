package com.example.employeeservice.humanresourcessubdomain.businesslayer.department;


import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.Department;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.DepartmentIdentifier;
import com.example.employeeservice.humanresourcessubdomain.dataaccesslayer.department.DepartmentRepository;
import com.example.employeeservice.humanresourcessubdomain.mapperlayer.DepartmentRequestMapper;
import com.example.employeeservice.humanresourcessubdomain.mapperlayer.DepartmentResponseMapper;
import com.example.employeeservice.humanresourcessubdomain.presentationlayer.department.DepartmentRequestModel;
import com.example.employeeservice.humanresourcessubdomain.presentationlayer.department.DepartmentResponseModel;
import com.example.employeeservice.utils.exceptions.NotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Christine Gerard
 * @created 02/02/2024
 * @project cardealership-ws-2024
 */

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentResponseMapper departmentResponseMapper;
    private final DepartmentRequestMapper departmentRequestMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentResponseMapper departmentResponseMapper, DepartmentRequestMapper departmentRequestMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentResponseMapper = departmentResponseMapper;
        this.departmentRequestMapper = departmentRequestMapper;
    }

    @Override
    public List<DepartmentResponseModel> getAllDepartments() {

        List<Department> departmentList = departmentRepository.findAll();

        /*
        List<DepartmentResponseModel> departmentResponseModelList = new ArrayList<>();

        departmentList.forEach(department -> {
            DepartmentResponseModel departmentResponseModel = new DepartmentResponseModel();
            BeanUtils.copyProperties(department, departmentResponseModel);
            departmentResponseModel.setDepartmentId(department.getDepartmentIdentifier().getDepartmentId());
            departmentResponseModelList.add(departmentResponseModel);
        });
        */

        return departmentResponseMapper.entityListToResponseModelList(departmentList);
    }

    @Override
    public DepartmentResponseModel getDepartmentByDepartmentId(String departmentId) {

        Department foundDepartment = departmentRepository.findDepartmentByDepartmentIdentifier_DepartmentId(departmentId);

        if (foundDepartment == null) {
            throw new NotFoundException("Unknown department id: " + departmentId);
        }

        /*DepartmentResponseModel departmentResponseModel = new DepartmentResponseModel();
        BeanUtils.copyProperties(foundDepartment, departmentResponseModel);
        departmentResponseModel.setDepartmentId(foundDepartment.getDepartmentIdentifier().getDepartmentId());
        */

        return departmentResponseMapper.entityToResponseModel(foundDepartment);
    }

    @Override
    public DepartmentResponseModel addDepartment(DepartmentRequestModel departmentRequestModel) {

        Department department = departmentRequestMapper.requestModelToEntity(departmentRequestModel, new DepartmentIdentifier());
        return departmentResponseMapper.entityToResponseModel(departmentRepository.save(department));

        /* Department department = new Department();
        BeanUtils.copyProperties(departmentRequestModel, department);
        department.setDepartmentIdentifier(new DepartmentIdentifier());

        Department savedDepartment = departmentRepository.save(department);

        DepartmentResponseModel departmentResponseModel = new DepartmentResponseModel();
        BeanUtils.copyProperties(savedDepartment, departmentResponseModel);
        departmentResponseModel.setDepartmentId(savedDepartment.getDepartmentIdentifier().getDepartmentId());

        return departmentResponseModel; */
    }

    @Override
    public DepartmentResponseModel updateDepartment(DepartmentRequestModel departmentRequestModel, String departmentId) {

        Department foundDepartment = departmentRepository.findDepartmentByDepartmentIdentifier_DepartmentId(departmentId);

        if (foundDepartment == null) {
            throw new NotFoundException("Unknown department id: " + departmentId);
        }

        Department department = departmentRequestMapper.requestModelToEntity(departmentRequestModel, foundDepartment.getDepartmentIdentifier());
        department.setId(foundDepartment.getId());

        return departmentResponseMapper.entityToResponseModel(departmentRepository.save(department));

        /*
        Department updateDepartment = new Department();
        BeanUtils.copyProperties(departmentRequestModel, updateDepartment);
        updateDepartment.setDepartmentIdentifier(foundDepartment.getDepartmentIdentifier());
        updateDepartment.setId(foundDepartment.getId());

        Department savedDepartment = departmentRepository.save(updateDepartment);

        DepartmentResponseModel departmentResponseModel = new DepartmentResponseModel();
        BeanUtils.copyProperties(savedDepartment, departmentResponseModel);
        departmentResponseModel.setDepartmentId(savedDepartment.getDepartmentIdentifier().getDepartmentId());

        return departmentResponseModel;
        */


    }

    @Override
    public void removeDepartmentByDepartmentId(String departmentId) {

        Department foundDepartment = departmentRepository.findDepartmentByDepartmentIdentifier_DepartmentId(departmentId);

        if (foundDepartment == null) {
            throw new NotFoundException("Unknown department id: " + departmentId);
        }

        departmentRepository.delete(foundDepartment);
    }

}
